package com.bipul.fintrack.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bipul.fintrack.data.local.entity.BudgetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(
        budget: BudgetEntity
    )

    @Delete
    suspend fun deleteBudget(
        budget: BudgetEntity
    )

    @Query(
        "SELECT * FROM budgets ORDER BY budgetId DESC"
    )
    fun getAllBudgets(): Flow<List<BudgetEntity>>


    @Query(
        "SELECT * FROM budgets WHERE month = :month"
    )
    fun getBudgetsByMonth(
        month: String
    ): Flow<List<BudgetEntity>>


    @Query(
        "SELECT * FROM budgets WHERE categoryId = :categoryId"
    )
    fun getBudgetsByCategory(
        categoryId: Long
    ): Flow<List<BudgetEntity>>


    // NEW
    @Query("""
        SELECT EXISTS(
            SELECT 1
            FROM budgets
            WHERE categoryId = :categoryId
            AND month = :month
        )
    """)
    suspend fun existsBudget(
        categoryId: Long,
        month: String
    ): Boolean
}