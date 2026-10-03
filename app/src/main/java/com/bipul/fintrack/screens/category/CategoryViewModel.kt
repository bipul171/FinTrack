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
                name = name,
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
}