package com.bipul.fintrack.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bipul.fintrack.data.local.dao.TransactionDao
import com.bipul.fintrack.data.local.entity.TransactionEntity

@Database(
    entities = [TransactionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FinTrackDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao
}