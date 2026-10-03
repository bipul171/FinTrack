package com.bipul.fintrack.data.local.database

import com.bipul.fintrack.data.local.dao.CategoryDao
import com.bipul.fintrack.data.local.entity.CategoryEntity
import javax.inject.Inject

class CategorySeeder @Inject constructor(
    private val categoryDao: CategoryDao
) {

    suspend fun seedDefaultCategories() {
        val existingCategories = categoryDao.getAllCategoriesOnce()

        if (existingCategories.isNotEmpty()) {
            return
        }

        val defaultCategories = listOf(
            CategoryEntity(
                name = "Food",
                type = "Expense"
            ),
            CategoryEntity(
                name = "Transport",
                type = "Expense"
            ),
            CategoryEntity(
                name = "Entertainment",
                type = "Expense"
            ),
            CategoryEntity(
                name = "Shopping",
                type = "Expense"
            ),
            CategoryEntity(
                name = "Bills",
                type = "Expense"
            ),
            CategoryEntity(
                name = "Others",
                type = "Expense"
            )
        )

        defaultCategories.forEach { category ->
            categoryDao.insertCategory(category)
        }
    }
}