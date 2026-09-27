package com.bipul.fintrack.screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bipul.fintrack.data.local.entity.CategoryEntity
import com.bipul.fintrack.data.local.entity.TransactionEntity
import com.bipul.fintrack.screens.category.CategoryViewModel
import com.bipul.fintrack.screens.transaction.TransactionViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: TransactionViewModel = hiltViewModel()
) {
    var showIncomeDialog by remember {
        mutableStateOf(false)
    }

    var showExpenseDialog by remember {
        mutableStateOf(false)
    }

    val transactions by viewModel.currentMonthTransactions.collectAsState(
        initial = emptyList()
    )

    val categoryViewModel: CategoryViewModel = hiltViewModel()

    val categories by categoryViewModel.categories.collectAsState(
        initial = emptyList()
    )

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

    val totalBalance = totalIncome - totalExpense

    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            NavigationBar {

                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home"
                        )
                    },
                    label = {
                        Text(
                            text = "Home"
                        )
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.navigate("transaction")
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.ReceiptLong,
                            contentDescription = "Transactions"
                        )
                    },
                    label = {
                        Text(
                            text = "Transactions"
                        )
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.navigate("budget")
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.AccountBalanceWallet,
                            contentDescription = "Budget"
                        )
                    },
                    label = {
                        Text(
                            text = "Budget"
                        )
                    }
                )
            }
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                HomeHeader()
            }

            item {
                TotalBalanceCard(
                    balance = totalBalance
                )
            }

            item {
                IncomeExpenseSection(
                    income = totalIncome,
                    expense = totalExpense
                )
            }

            item {
                QuickActionsSection(
                    onIncomeClick = {
                        showIncomeDialog = true
                    },
                    onExpenseClick = {
                        showExpenseDialog = true
                    }
                )
            }

            item {
                RecentTransactionsSection(
                    transactions = transactions
                )
            }
        }
    }

    // Income Dialog
    if (showIncomeDialog) {

        AmountDialog(
            title = "Add Income",
            confirmText = "Add Income",
            onDismiss = {
                showIncomeDialog = false
            },
            onConfirm = { amount ->

                viewModel.addTransaction(
                    amount = amount,
                    transactionType = "Income",
                    note = "Quick Income"
                )

                showIncomeDialog = false

                Toast.makeText(
                    context,
                    "Income added successfully",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
    }

    // Expense Dialog
    if (showExpenseDialog) {

        ExpenseDialog(
            categories = categories,
            onDismiss = {
                showExpenseDialog = false
            },
            onConfirm = { amount, categoryId ->

                viewModel.addTransaction(
                    amount = amount,
                    transactionType = "Expense",
                    note = "Quick Expense",
                    categoryId = categoryId
                )

                showExpenseDialog = false

                Toast.makeText(
                    context,
                    "Expense added successfully",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
    }
}


@Composable
fun HomeHeader() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column {

            Text(
                text = "Good Evening,",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = "Md. Bipul Mia",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = "🔔",
            fontSize = 28.sp
        )
    }
}


@Composable
fun TotalBalanceCard(
    balance: Double
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Total Balance",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "৳ ${"%.2f".format(balance)}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun IncomeExpenseSection(
    income: Double,
    expense: Double
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Card(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(20.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Income",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "৳ ${"%.2f".format(income)}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Card(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            shape = RoundedCornerShape(20.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Expense",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "৳ ${"%.2f".format(expense)}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Composable
fun QuickActionsSection(
    onIncomeClick: () -> Unit,
    onExpenseClick: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Quick Actions",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Button(
                onClick = onIncomeClick,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp)
            ) {

                Text(
                    text = "+ Income",
                    fontSize = 16.sp
                )
            }

            OutlinedButton(
                onClick = onExpenseClick,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp)
            ) {

                Text(
                    text = "- Expense",
                    fontSize = 16.sp
                )
            }
        }
    }
}


@Composable
fun AmountDialog(
    title: String,
    confirmText: String,
    onDismiss: () -> Unit,
    onConfirm: (Double) -> Unit
) {

    var amountText by remember {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = onDismiss,

        title = {
            Text(
                text = title
            )
        },

        text = {

            OutlinedTextField(
                value = amountText,

                onValueChange = {
                    amountText = it
                },

                label = {
                    Text(
                        text = "Amount"
                    )
                },

                singleLine = true
            )
        },

        confirmButton = {

            Button(
                onClick = {

                    val amount =
                        amountText.toDoubleOrNull()

                    if (
                        amount != null &&
                        amount > 0
                    ) {
                        onConfirm(amount)
                    }
                }
            ) {

                Text(
                    text = confirmText
                )
            }
        },

        dismissButton = {

            OutlinedButton(
                onClick = onDismiss
            ) {

                Text(
                    text = "Cancel"
                )
            }
        }
    )
}


@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDialog(
    categories: List<CategoryEntity>,
    onDismiss: () -> Unit,
    onConfirm: (Double, Long) -> Unit
) {

    var amountText by remember {
        mutableStateOf("")
    }

    var selectedCategory by remember {
        mutableStateOf("")
    }

    var selectedCategoryId by remember {
        mutableStateOf<Long?>(null)
    }

    var categoryExpanded by remember {
        mutableStateOf(false)
    }

    val expenseCategories = categories.filter {
        it.type.equals(
            "Expense",
            ignoreCase = true
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,

        title = {
            Text(
                text = "Add Expense"
            )
        },

        text = {

            Column {

                OutlinedTextField(
                    value = amountText,

                    onValueChange = {
                        amountText = it
                    },

                    label = {
                        Text(
                            text = "Amount"
                        )
                    },

                    singleLine = true,

                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                ExposedDropdownMenuBox(
                    expanded = categoryExpanded,

                    onExpandedChange = {
                        categoryExpanded = !categoryExpanded
                    }
                ) {

                    OutlinedTextField(
                        value = selectedCategory,

                        onValueChange = {},

                        readOnly = true,

                        label = {
                            Text(
                                text = "Category"
                            )
                        },

                        placeholder = {
                            Text(
                                text = "Select category"
                            )
                        },

                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = categoryExpanded
                            )
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = categoryExpanded,

                        onDismissRequest = {
                            categoryExpanded = false
                        }
                    ) {

                        expenseCategories.forEach { category ->

                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = category.name
                                    )
                                },

                                onClick = {

                                    selectedCategory =
                                        category.name

                                    selectedCategoryId =
                                        category.categoryId

                                    categoryExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        },

        confirmButton = {

            Button(
                onClick = {

                    val amount =
                        amountText.toDoubleOrNull()

                    if (
                        amount != null &&
                        amount > 0 &&
                        selectedCategoryId != null
                    ) {

                        onConfirm(
                            amount,
                            selectedCategoryId!!
                        )
                    }
                }
            ) {

                Text(
                    text = "Add Expense"
                )
            }
        },

        dismissButton = {

            OutlinedButton(
                onClick = onDismiss
            ) {

                Text(
                    text = "Cancel"
                )
            }
        }
    )
}


@Composable
fun RecentTransactionsSection(
    transactions: List<TransactionEntity>
) {

    val recentTransactions = transactions
        .sortedByDescending {
            it.date
        }
        .take(5)

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Recent Transactions",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                if (recentTransactions.isEmpty()) {

                    Text(
                        text = "No transactions yet.",
                        fontSize = 16.sp
                    )

                } else {

                    recentTransactions.forEachIndexed {
                            index,
                            transaction ->

                        val sign =
                            if (
                                transaction.transactionType.equals(
                                    "Income",
                                    ignoreCase = true
                                )
                            ) {
                                "+"
                            } else {
                                "-"
                            }

                        val title =
                            transaction.note.ifBlank {
                                transaction.transactionType
                            }

                        TransactionItem(
                            title = title,
                            amount = "$sign৳ ${"%.2f".format(transaction.amount)}"
                        )

                        if (
                            index <
                            recentTransactions.lastIndex
                        ) {
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun TransactionItem(
    title: String,
    amount: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),

        horizontalArrangement =
            Arrangement.SpaceBetween,

        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        Text(
            text = amount,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
