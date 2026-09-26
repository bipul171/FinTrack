package com.bipul.fintrack.screens.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bipul.fintrack.data.local.entity.TransactionEntity
import com.bipul.fintrack.navigation.AppRoutes

@Composable
fun TransactionScreen(
    navHostController: NavHostController,
    viewModel: TransactionViewModel = hiltViewModel()
) {

    val transactions by viewModel.transactions.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            Text(
                text = "Transactions",
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        item {
            Button(
                onClick = {
                    navHostController.navigate(
                        AppRoutes.AddTransaction.route
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Add Transaction"
                )
            }
        }

        item {
            TransactionSummaryCard(
                transactions = transactions
            )
        }

        item {
            TransactionList(
                transactions = transactions
            )
        }
    }
}

@Composable
fun TransactionSummaryCard(
    transactions: List<TransactionEntity>
) {

    val totalExpense = transactions
        .filter { it.transactionType.equals("Expense", ignoreCase = true) }
        .sumOf { it.amount }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = "This Month",
                fontSize = 16.sp
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "৳ %.2f Spent".format(totalExpense),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun TransactionList(
    transactions: List<TransactionEntity>
) {

    Column {

        Text(
            text = "Recent Transactions",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        if (transactions.isEmpty()) {

            Text(
                text = "No transactions yet."
            )

        } else {

            transactions.forEach { transaction ->

                val sign = if (
                    transaction.transactionType.equals(
                        "Income",
                        ignoreCase = true
                    )
                ) {
                    "+"
                } else {
                    "-"
                }

                Text(
                    text = "${transaction.note.ifBlank { transaction.transactionType }}   $sign৳ ${"%.2f".format(transaction.amount)}"
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }
        }
    }
}