package com.bipul.fintrack.screens.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bipul.fintrack.data.local.entity.CategoryEntity
import com.bipul.fintrack.data.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: CategoryRepository
) : ViewModel() {

    val categories: Flow<List<CategoryEntity>> =
        repository.getAllCategories()

    fun addCategory(
        name: String,
        type: String
    ) {
        viewModelScope.launch {

            val category = CategoryEntity(
                name = name.trim(),
                type = type
            )

            repository.insertCategory(category)
        }
    }

    fun deleteCategory(category: CategoryEntity) {
        viewModelScope.launch {
            repository.deleteCategory(category)
        }
    }

    fun findOrCreateCategory(
        name: String,
        type: String,
        onResult: (Long) -> Unit
    ) {
        viewModelScope.launch {

            val normalizedName = name.trim()

            if (normalizedName.isEmpty()) {
                return@launch
            }

            val existingCategory = repository
                .getAllCategoriesOnce()
                .firstOrNull {
                    it.name.equals(normalizedName, ignoreCase = true) &&
                            it.type.equals(type, ignoreCase = true)
                }

            val categoryId = if (existingCategory != null) {

                existingCategory.categoryId

            } else {

                repository.insertCategory(
                    CategoryEntity(
                        name = normalizedName,
                        type = type
                    )
                )
            }

            onResult(categoryId)
        }
    }
}