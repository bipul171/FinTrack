package com.bipul.fintrack.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bipul.fintrack.data.local.dao.BudgetDao
import com.bipul.fintrack.data.local.dao.CategoryDao
import com.bipul.fintrack.data.local.dao.TransactionDao
import com.bipul.fintrack.data.local.entity.BudgetEntity
import com.bipul.fintrack.data.local.entity.CategoryEntity
import com.bipul.fintrack.data.local.entity.TransactionEntity

@Database(
    entities = [
        TransactionEntity::class,
        CategoryEntity::class,
        BudgetEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class FinTrackDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao

    abstract fun categoryDao(): CategoryDao

    abstract fun budgetDao(): BudgetDao
}