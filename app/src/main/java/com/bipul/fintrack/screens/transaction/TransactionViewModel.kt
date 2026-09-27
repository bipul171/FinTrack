package com.bipul.fintrack.screens.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bipul.fintrack.data.local.entity.TransactionEntity
import com.bipul.fintrack.data.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {

    val transactions: Flow<List<TransactionEntity>> =
        repository.getAllTransactions()

    val currentMonthTransactions: Flow<List<TransactionEntity>> =
        repository.getTransactionsBetween(
            startDate = getStartOfCurrentMonth(),
            endDate = getStartOfNextMonth()
        )

    fun addTransaction(
        amount: Double,
        transactionType: String,
        note: String = "",
        categoryId: Long? = null,
        userId: Long? = null
    ) {
        viewModelScope.launch {

            val transaction = TransactionEntity(
                amount = amount,
                transactionType = transactionType,
                note = note,
                categoryId = categoryId,
                userId = userId
            )

            repository.insertTransaction(transaction)
        }
    }

    fun deleteTransaction(
        transaction: TransactionEntity
    ) {
        viewModelScope.launch {
            repository.deleteTransaction(transaction)
        }
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