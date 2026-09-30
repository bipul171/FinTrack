package com.bipul.fintrack.screens.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bipul.fintrack.data.local.entity.BudgetEntity
import com.bipul.fintrack.data.repository.BudgetRepository
import com.bipul.fintrack.data.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val repository: BudgetRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    // Current month budgets only
    val budgets: Flow<List<BudgetEntity>> =
        repository.getBudgetsByMonth(
            getCurrentMonth()
        )

    val categoryExpenses: StateFlow<Map<Long, Double>> =
        transactionRepository
            .getTransactionsBetween(
                startDate = getStartOfCurrentMonth(),
                endDate = getStartOfNextMonth()
            )
            .map { transactions ->

                transactions
                    .filter {
                        it.transactionType == "Expense" &&
                                it.categoryId != null
                    }
                    .groupBy {
                        it.categoryId!!
                    }
                    .mapValues { entry ->
                        entry.value.sumOf {
                            it.amount
                        }
                    }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyMap()
            )

    fun addBudget(
        categoryId: Long,
        amount: Double,
        month: String,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {

            val exists = repository.existsBudget(
                categoryId = categoryId,
                month = month
            )

            if (exists) {
                onResult(false)
                return@launch
            }

            val budget = BudgetEntity(
                categoryId = categoryId,
                amount = amount,
                month = month
            )

            repository.insertBudget(budget)

            onResult(true)
        }
    }

    fun deleteBudget(
        budget: BudgetEntity
    ) {
        viewModelScope.launch {
            repository.deleteBudget(budget)
        }
    }

    private fun getCurrentMonth(): String {

        return LocalDate
            .now()
            .format(
                DateTimeFormatter.ofPattern(
                    "MMMM yyyy",
                    Locale.ENGLISH
                )
            )
    }

    private fun getStartOfCurrentMonth(): Long {

        val startOfMonth = LocalDate
            .now()
            .withDayOfMonth(1)

        return startOfMonth
            .atStartOfDay(
                ZoneId.systemDefault()
            )
            .toInstant()
            .toEpochMilli()
    }

    private fun getStartOfNextMonth(): Long {

        val startOfNextMonth = LocalDate
            .now()
            .withDayOfMonth(1)
            .plusMonths(1)

        return startOfNextMonth
            .atStartOfDay(
                ZoneId.systemDefault()
            )
            .toInstant()
            .toEpochMilli()
    }
}