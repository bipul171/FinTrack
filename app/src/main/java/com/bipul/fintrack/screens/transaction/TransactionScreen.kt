package com.bipul.fintrack.screens.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bipul.fintrack.navigation.AppRoutes

@Composable
fun TransactionScreen(navHostController : NavHostController) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp)
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
                    navHostController.navigate(AppRoutes.AddTransaction.route)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Add Transaction"
                )
            }
        }
        item {
            TransactionSummaryCard()
        }
        item {
            TransactionList()
        }
    }
}

@Composable
fun TransactionSummaryCard() {

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
                text = "৳ 15,000 Spent",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun TransactionList() {

    Column {

        Text(
            text = "Recent Transactions",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "Food       -৳ 500"
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "Salary     +৳ 30,000"
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "Transport  -৳ 100"
        )
    }
}