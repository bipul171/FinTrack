package com.bipul.fintrack.data.repository

import com.bipul.fintrack.data.local.dao.BudgetDao
import com.bipul.fintrack.data.local.entity.BudgetEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BudgetRepository @Inject constructor(
    private val budgetDao: BudgetDao
) {

    fun getAllBudgets(): Flow<List<BudgetEntity>> {
        return budgetDao.getAllBudgets()
    }

    fun getBudgetsByMonth(
        month: String
    ): Flow<List<BudgetEntity>> {
        return budgetDao.getBudgetsByMonth(month)
    }

    fun getBudgetsByCategory(
        categoryId: Long
    ): Flow<List<BudgetEntity>> {
        return budgetDao.getBudgetsByCategory(categoryId)
    }

    suspend fun insertBudget(
        budget: BudgetEntity
    ) {
        budgetDao.insertBudget(budget)
    }

    suspend fun deleteBudget(
        budget: BudgetEntity
    ) {
        budgetDao.deleteBudget(budget)
    }
}