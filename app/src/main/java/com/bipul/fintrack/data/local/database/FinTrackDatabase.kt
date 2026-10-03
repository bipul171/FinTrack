package com.bipul.fintrack.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bipul.fintrack.data.local.dao.BudgetDao
import com.bipul.fintrack.data.local.dao.CategoryDao
import com.bipul.fintrack.data.local.dao.TransactionDao
import com.bipul.fintrack.data.local.dao.UserDao
import com.bipul.fintrack.data.local.entity.BudgetEntity
import com.bipul.fintrack.data.local.entity.CategoryEntity
import com.bipul.fintrack.data.local.entity.TransactionEntity
import com.bipul.fintrack.data.local.entity.UserEntity

@Database(
    entities = [
        TransactionEntity::class,
        CategoryEntity::class,
        BudgetEntity::class,
        UserEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class FinTrackDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao

    abstract fun categoryDao(): CategoryDao

    abstract fun budgetDao(): BudgetDao

    abstract fun userDao(): UserDao
}