package com.bipul.fintrack.screens.home

import android.widget.Toast
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
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bipul.fintrack.data.local.entity.CategoryEntity
import com.bipul.fintrack.data.local.entity.TransactionEntity
import com.bipul.fintrack.navigation.AppRoutes
import com.bipul.fintrack.screens.category.CategoryViewModel
import com.bipul.fintrack.screens.transaction.TransactionViewModel
import java.util.Calendar

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

    val totalTransactions = transactions.size

    val maxAmount = maxOf(
        totalIncome,
        totalExpense,
        1.0
    )

    val incomeProgress =
        (totalIncome / maxAmount).toFloat()

    val expenseProgress =
        (totalExpense / maxAmount).toFloat()

    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = if (isSystemInDarkTheme()) {
                    Color(0xFF1C2115)
                } else {
                    Color(0xFFF5F7FA)
                },
                tonalElevation = 8.dp
            ) {

                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home",
                            modifier = Modifier.size(26.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Home",
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = if (isSystemInDarkTheme()) {
                            Color(0xFFE8F5A8)
                        } else {
                            Color(0xFF365314)
                        },
                        selectedTextColor = if (isSystemInDarkTheme()) {
                            Color(0xFFE8F5A8)
                        } else {
                            Color(0xFF365314)
                        },
                        indicatorColor = if (isSystemInDarkTheme()) {
                            Color(0xFF4B5318)
                        } else {
                            Color(0xFFD9E88A)
                        },
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.navigate("transaction")
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.ReceiptLong,
                            contentDescription = "Transactions",
                            modifier = Modifier.size(26.dp)
                        )
                    },
                    label = {
                        Text("Transactions")
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.navigate("budget")
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.AccountBalanceWallet,
                            contentDescription = "Budget",
                            modifier = Modifier.size(26.dp)
                        )
                    },
                    label = {
                        Text("Budget")
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
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
                HomeHeader(
                    onProfileClick = {
                        navController.navigate(AppRoutes.Profile.route)
                    }
                )
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

                MonthlySummarySection(
                    totalIncome = totalIncome,
                    totalExpense = totalExpense,
                    totalTransactions = totalTransactions,
                    incomeProgress = incomeProgress,
                    expenseProgress = expenseProgress
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                RecentTransactionsSection(
                    transactions = transactions,
                    categoryMap = categories.associateBy { it.categoryId }
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
fun HomeHeader(
    onProfileClick: () -> Unit
) {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val isDark = isSystemInDarkTheme()

    val greeting = when (currentHour) {
        in 5..11 -> "Good Morning,"
        in 12..16 -> "Good Afternoon,"
        in 17..20 -> "Good Evening,"
        else -> "Good Night,"
    }

    val greetingColor = if (isDark) {
        Color(0xFFCBD5E1)
    } else {
        Color(0xFF64748B)
    }

    val nameColor = if (isDark) {
        Color(0xFFE0E7FF)
    } else {
        Color(0xFF1E3A8A)
    }

    val profileBackground = if (isDark) {
        Color(0xFF312E81)
    } else {
        Color(0xFFE0E7FF)
    }

    val profileIconColor = if (isDark) {
        Color(0xFFC7D2FE)
    } else {
        Color(0xFF3730A3)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Text(
                text = greeting,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = greetingColor
            )

            Text(
                text = "Md. Bipul Mia",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = nameColor
            )
        }

        IconButton(
            onClick = onProfileClick,
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = profileBackground,
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile",
                tint = profileIconColor
            )
        }
    }
}

@Composable
fun TotalBalanceCard(
    balance: Double
) {
    val isDark = isSystemInDarkTheme()

    val backgroundColor = if (isDark) {
        Color(0xFF1E3A8A)
    } else {
        Color(0xFFE0E7FF)
    }

    val titleColor = if (isDark) {
        Color(0xFFDCE7FF)
    } else {
        Color(0xFF3730A3)
    }

    val amountColor = if (isDark) {
        Color.White
    } else {
        Color(0xFF1E1B4B)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Total Balance",
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                color = titleColor
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "৳ ${"%.2f".format(balance)}",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = amountColor
            )
        }
    }
}


@Composable
fun IncomeExpenseSection(
    income: Double,
    expense: Double
) {
    val isDark = isSystemInDarkTheme()

    val incomeBackground = if (isDark) {
        Color(0xFF123B2A)
    } else {
        Color(0xFFE8F5E9)
    }

    val incomeColor = if (isDark) {
        Color(0xFF86EFAC)
    } else {
        Color(0xFF15803D)
    }

    val expenseBackground = if (isDark) {
        Color(0xFF451A1A)
    } else {
        Color(0xFFFFEBEE)
    }

    val expenseColor = if (isDark) {
        Color(0xFFFCA5A5)
    } else {
        Color(0xFFDC2626)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Card(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = incomeBackground
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Income",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = incomeColor
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "৳ ${"%.2f".format(income)}",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = incomeColor
                )
            }
        }

        Card(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = expenseBackground
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Expense",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = expenseColor
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "৳ ${"%.2f".format(expense)}",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = expenseColor
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
    val isDark = isSystemInDarkTheme()

    val incomeBackground = if (isDark) {
        Color(0xFF166534)
    } else {
        Color(0xFFDCFCE7)
    }

    val incomeContent = if (isDark) {
        Color(0xFFDCFCE7)
    } else {
        Color(0xFF15803D)
    }

    val expenseBackground = if (isDark) {
        Color(0xFF991B1B)
    } else {
        Color(0xFFFEE2E2)
    }

    val expenseContent = if (isDark) {
        Color(0xFFFEE2E2)
    } else {
        Color(0xFFDC2626)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Quick Actions",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Button(
                onClick = onIncomeClick,
                modifier = Modifier
                    .weight(1f)
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = incomeBackground,
                    contentColor = incomeContent
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Income"
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Income",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Button(
                onClick = onExpenseClick,
                modifier = Modifier
                    .weight(1f)
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = expenseBackground,
                    contentColor = expenseContent
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Add Expense"
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Expense",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
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
    transactions: List<TransactionEntity>,
    categoryMap: Map<Long, CategoryEntity>
) {
    val recentTransactions = transactions
        .sortedByDescending { it.date }
        .take(5)

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Recent Transactions",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                    alpha = 0.45f
                )
            )
        ) {
            if (recentTransactions.isEmpty()) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.ReceiptLong,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "No transactions yet.",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

            } else {

                Column(
                    modifier = Modifier.padding(vertical = 6.dp)
                ) {
                    recentTransactions.forEach { transaction ->

                        TransactionItem(
                            transaction = transaction,
                            category = transaction.categoryId?.let {
                                categoryMap[it]
                            }
                        )

                        if (transaction != recentTransactions.last()) {
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun TransactionItem(
    transaction: TransactionEntity,
    category: CategoryEntity?
) {
    val isIncome = transaction.transactionType.equals(
        "Income",
        ignoreCase = true
    )

    val accentColor = if (isIncome) {
        if (isSystemInDarkTheme()) {
            Color(0xFF4ADE80)
        } else {
            Color(0xFF16A34A)
        }
    } else {
        if (isSystemInDarkTheme()) {
            Color(0xFFF87171)
        } else {
            Color(0xFFDC2626)
        }
    }

    val iconBackground = if (isIncome) {
        if (isSystemInDarkTheme()) {
            Color(0xFF14532D)
        } else {
            Color(0xFFDCFCE7)
        }
    } else {
        if (isSystemInDarkTheme()) {
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

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 14.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(44.dp)
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
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = transaction.note.ifBlank {
                    transaction.transactionType
                },
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(3.dp))

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
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = accentColor
        )
    }
}

@Composable
fun MonthlySummarySection(
    totalIncome: Double,
    totalExpense: Double,
    totalTransactions: Int,
    incomeProgress: Float,
    expenseProgress: Float
) {
    val isDark = isSystemInDarkTheme()

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

    val progressBackground = if (isDark) {
        Color(0xFF334155)
    } else {
        Color(0xFFE2E8F0)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Monthly Summary",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                    alpha = 0.45f
                )
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                // Income
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Income",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = incomeColor
                    )

                    Text(
                        text = "৳ ${"%.2f".format(totalIncome)}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = incomeColor
                    )
                }

                LinearProgressIndicator(
                    progress = { incomeProgress.coerceIn(0f, 1f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = incomeColor,
                    trackColor = progressBackground
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Expense
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Expense",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = expenseColor
                    )

                    Text(
                        text = "৳ ${"%.2f".format(totalExpense)}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = expenseColor
                    )
                }

                LinearProgressIndicator(
                    progress = { expenseProgress.coerceIn(0f, 1f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = expenseColor,
                    trackColor = progressBackground
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Transactions",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = totalTransactions.toString(),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}