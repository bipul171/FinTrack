package com.bipul.fintrack.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budgets")
data class BudgetEntity(

    @PrimaryKey(autoGenerate = true)
    val budgetId: Long = 0,

    val categoryId: Long,

    val amount: Double,

    val month: String
)