package com.bipul.fintrack.screens.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bipul.fintrack.data.local.entity.CategoryEntity
import com.bipul.fintrack.data.local.entity.TransactionEntity
import com.bipul.fintrack.navigation.AppRoutes
import com.bipul.fintrack.screens.category.CategoryViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TransactionScreen(
    navHostController: NavHostController,
    viewModel: TransactionViewModel = hiltViewModel()
) {
    val isDark = isSystemInDarkTheme()

    val transactions by viewModel.transactions.collectAsState(
        initial = emptyList()
    )

    val categoryViewModel: CategoryViewModel = hiltViewModel()

    val categories by categoryViewModel.categories.collectAsState(
        initial = emptyList()
    )

    val categoryMap = categories.associateBy {
        it.categoryId
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            Spacer(
                modifier = Modifier.height(8.dp)
            )

            TransactionHeader(
                isDark = isDark
            )
        }

        item {
            Button(
                onClick = {
                    navHostController.navigate(
                        AppRoutes.AddTransaction.route
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(17.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Transaction",
                    modifier = Modifier.size(24.dp)
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text(
                    text = "Add Transaction",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        item {
            TransactionSummaryCard(
                transactions = transactions,
                isDark = isDark
            )
        }

        item {
            TransactionList(
                transactions = transactions,
                categoryMap = categoryMap,
                onDelete = { transaction ->
                    viewModel.deleteTransaction(transaction)
                },
                isDark = isDark
            )
        }

        item {
            Spacer(
                modifier = Modifier.height(8.dp)
            )
        }
    }
}

@Composable
fun TransactionHeader(
    isDark: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Transactions",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = if (isDark) {
                Color(0xFFF1F5F9)
            } else {
                Color(0xFF172554)
            }
        )

        Spacer(
            modifier = Modifier.height(5.dp)
        )

        Text(
            text = "Track your income and expenses",
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = if (isDark) {
                Color(0xFFCBD5E1)
            } else {
                Color(0xFF64748B)
            }
        )
    }
}

@Composable
fun TransactionSummaryCard(
    transactions: List<TransactionEntity>,
    isDark: Boolean
) {
    val totalIncome = transactions
        .filter {
            it.transactionType.equals(
                "Income",
                ignoreCase = true
            )
        }
        .sumOf {
            it.amount
        }

    val totalExpense = transactions
        .filter {
            it.transactionType.equals(
                "Expense",
                ignoreCase = true
            )
        }
        .sumOf {
            it.amount
        }

    val balance = totalIncome - totalExpense

    val incomeColor = if (isDark) {
        Color(0xFF4ADE80)
    } else {
        Color(0xFF16A34A)
    }

    val expenseColor = if (isDark) {
        Color(0xFFF87171)
    } else {
        Color(0xFFDC2626)
    }

    val balanceColor = if (balance >= 0) {
        incomeColor
    } else {
        expenseColor
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            Text(
                text = "This Month",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                SummaryItem(
                    modifier = Modifier.weight(1f),
                    title = "Income",
                    amount = totalIncome,
                    color = incomeColor
                )

                SummaryItem(
                    modifier = Modifier.weight(1f),
                    title = "Expense",
                    amount = totalExpense,
                    color = expenseColor
                )
            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            HorizontalDivider()

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Balance",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = "৳ %.2f".format(balance),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = balanceColor
                )
            }
        }
    }
}

@Composable
fun SummaryItem(
    modifier: Modifier,
    title: String,
    amount: Double,
    color: Color
) {
    Column(
        modifier = modifier
    ) {

        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = color
        )

        Spacer(
            modifier = Modifier.height(5.dp)
        )

        Text(
            text = "৳ %.2f".format(amount),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Composable
fun TransactionList(
    transactions: List<TransactionEntity>,
    categoryMap: Map<Long, CategoryEntity>,
    onDelete: (TransactionEntity) -> Unit,
    isDark: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Recent Transactions",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        if (transactions.isEmpty()) {

            EmptyTransactionState()

        } else {

            transactions.forEach { transaction ->

                TransactionCard(
                    transaction = transaction,
                    category = transaction.categoryId?.let {
                        categoryMap[it]
                    },
                    onDelete = {
                        onDelete(transaction)
                    },
                    isDark = isDark
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }
        }
    }
}

@Composable
fun TransactionCard(
    transaction: TransactionEntity,
    category: CategoryEntity?,
    onDelete: () -> Unit,
    isDark: Boolean
) {
    val isIncome = transaction.transactionType.equals(
        "Income",
        ignoreCase = true
    )

    val accentColor = if (isIncome) {
        if (isDark) {
            Color(0xFF4ADE80)
        } else {
            Color(0xFF16A34A)
        }
    } else {
        if (isDark) {
            Color(0xFFF87171)
        } else {
            Color(0xFFDC2626)
        }
    }

    val iconBackground = if (isIncome) {
        if (isDark) {
            Color(0xFF14532D)
        } else {
            Color(0xFFDCFCE7)
        }
    } else {
        if (isDark) {
            Color(0xFF7F1D1D)
        } else {
            Color(0xFFFEE2E2)
        }
    }

    val icon = if (isIncome) {
        Icons.Default.ArrowDownward
    } else {
        Icons.Default.ArrowUpward
    }

    val dateText = SimpleDateFormat(
        "dd MMM yyyy, hh:mm a",
        Locale.getDefault()
    ).format(
        Date(transaction.date)
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .background(
                            color = iconBackground,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = transaction.note.ifBlank {
                            transaction.transactionType
                        },
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(
                        modifier = Modifier.height(3.dp)
                    )

                    Text(
                        text = category?.name ?: "Uncategorized",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Text(
                    text = "${if (isIncome) "+" else "-"} ৳ ${
                        "%.2f".format(transaction.amount)
                    }",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentColor
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = dateText,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                OutlinedButton(
                    onClick = onDelete,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = if (isDark) {
                            Color(0xFFFCA5A5)
                        } else {
                            Color(0xFFDC2626)
                        }
                    )
                ) {

                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(6.dp)
                    )

                    Text(
                        text = "Delete",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyTransactionState() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = Icons.Default.ReceiptLong,
                contentDescription = null,
                modifier = Modifier.size(44.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "No transactions yet",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "Add your first transaction to get started.",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}