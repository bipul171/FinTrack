package com.bipul.fintrack.data.local.database

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseInitializer @Inject constructor(
    private val categorySeeder: CategorySeeder
) {

    fun initialize() {
        CoroutineScope(Dispatchers.IO).launch {
            categorySeeder.seedDefaultCategories()
        }
    }
}