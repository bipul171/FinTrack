package com.bipul.fintrack.di

import android.content.Context
import androidx.room.Room
import com.bipul.fintrack.data.local.dao.TransactionDao
import com.bipul.fintrack.data.local.database.FinTrackDatabase
import com.bipul.fintrack.data.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideFinTrackDatabase(
        @ApplicationContext context: Context
    ): FinTrackDatabase {
        return Room.databaseBuilder(
            context,
            FinTrackDatabase::class.java,
            "fintrack_database"
        ).build()
    }

    @Provides
    fun provideTransactionDao(
        database: FinTrackDatabase
    ): TransactionDao {
        return database.transactionDao()
    }

    @Provides
    fun provideTransactionRepository(
        transactionDao: TransactionDao
    ): TransactionRepository {
        return TransactionRepository(transactionDao)
    }
}