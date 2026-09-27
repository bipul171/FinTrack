package com.bipul.fintrack.screens.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bipul.fintrack.data.local.entity.BudgetEntity
import com.bipul.fintrack.data.repository.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val repository: BudgetRepository
) : ViewModel() {

    val budgets: Flow<List<BudgetEntity>> =
        repository.getAllBudgets()

    fun addBudget(
        categoryId: Long,
        amount: Double,
        month: String
    ) {
        viewModelScope.launch {

            val budget = BudgetEntity(
                categoryId = categoryId,
                amount = amount,
                month = month
            )

            repository.insertBudget(budget)
        }
    }

    fun deleteBudget(
        budget: BudgetEntity
    ) {
        viewModelScope.launch {
            repository.deleteBudget(budget)
        }
    }
}