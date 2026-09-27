package com.bipul.fintrack.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Query
import com.bipul.fintrack.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Delete
    suspend fun deleteTransaction(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Query("""
        SELECT COALESCE(SUM(amount), 0)
        FROM transactions
        WHERE categoryId = :categoryId
        AND transactionType = 'Expense'
    """)
    fun getExpenseByCategory(
        categoryId: Long
    ): Flow<Double>
}