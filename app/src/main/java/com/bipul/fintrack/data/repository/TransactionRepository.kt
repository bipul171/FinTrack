package com.bipul.fintrack.data.repository

import com.bipul.fintrack.data.local.dao.TransactionDao
import com.bipul.fintrack.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

class TransactionRepository(
    private val transactionDao: TransactionDao
) {

    suspend fun insertTransaction(
        transaction: TransactionEntity
    ) =
        transactionDao.insertTransaction(transaction)

    suspend fun deleteTransaction(
        transaction: TransactionEntity
    ) =
        transactionDao.deleteTransaction(transaction)

    fun getAllTransactions(): Flow<List<TransactionEntity>> =
        transactionDao.getAllTransactions()

    fun getExpenseByCategory(
        categoryId: Long
    ): Flow<Double> =
        transactionDao.getExpenseByCategory(categoryId)

    fun getTransactionsBetween(
        startDate: Long,
        endDate: Long
    ): Flow<List<TransactionEntity>> =
        transactionDao.getTransactionsBetween(
            startDate,
            endDate
        )
}