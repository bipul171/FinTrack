package com.bipul.fintrack.screens.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bipul.fintrack.data.local.entity.TransactionEntity
import com.bipul.fintrack.data.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {

    val transactions: Flow<List<TransactionEntity>> =
        repository.getAllTransactions()

    fun addTransaction(
        amount: Double,
        transactionType: String,
        note: String = "",
        categoryId: Long? = null,
        userId: Long? = null
    ) {
        viewModelScope.launch {

            val transaction = TransactionEntity(
                amount = amount,
                transactionType = transactionType,
                note = note,
                categoryId = categoryId,
                userId = userId
            )

            repository.insertTransaction(transaction)
        }
    }

    fun deleteTransaction(transaction: TransactionEntity) {
        viewModelScope.launch {
            repository.deleteTransaction(transaction)
        }
    }
}