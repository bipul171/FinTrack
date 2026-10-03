package com.bipul.fintrack.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(

    @PrimaryKey(autoGenerate = true)
    val transactionId: Long = 0,

    val amount: Double,

    val transactionType: String,

    val date: Long = System.currentTimeMillis(),

    val note: String = "",

    val categoryId: Long? = null,

    val userId: Long? = null
)