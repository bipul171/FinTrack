package com.bipul.fintrack.screens.budget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bipul.fintrack.data.local.entity.BudgetEntity
import com.bipul.fintrack.data.local.entity.CategoryEntity
import com.bipul.fintrack.navigation.AppRoutes
import com.bipul.fintrack.screens.category.CategoryViewModel

@Composable
fun BudgetScreen(
    navController: NavHostController
) {

    val budgetViewModel: BudgetViewModel = hiltViewModel()
    val categoryViewModel: CategoryViewModel = hiltViewModel()

    val budgets by budgetViewModel.budgets.collectAsState(
        initial = emptyList()
    )

    val categories by categoryViewModel.categories.collectAsState(
        initial = emptyList()
    )

    val categoryExpenses by budgetViewModel.categoryExpenses
        .collectAsState()

    val categoryMap = categories.associateBy {
        it.categoryId
    }

    val categoryNames = categories.associate {
        it.categoryId to it.name
    }

    val categoryExpensesList by budgetViewModel
        .getCategoryExpenses(categoryNames)
        .collectAsState(initial = emptyList())

    val totalBudget = budgets.sumOf {
        it.amount
    }

    val totalSpent = categoryExpenses.values.sum()

    val remaining = totalBudget - totalSpent

    val overallProgress =
        if (totalBudget > 0) {
            (totalSpent / totalBudget)
                .coerceIn(0.0, 1.0)
                .toFloat()
        } else {
            0f
        }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
            .padding(
                horizontal = 18.dp,
                vertical = 18.dp
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // ==========================================
        // HEADER
        // ==========================================

        item {

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "Budget",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "Track your spending and stay within your limits.",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // ==========================================
        // SUMMARY CARD
        // ==========================================

        item {

            BudgetSummaryCard(
                totalBudget = totalBudget,
                totalSpent = totalSpent,
                remaining = remaining
            )
        }

        // ==========================================
        // OVERVIEW
        // ==========================================

        item {

            BudgetOverviewSection(
                totalBudget = totalBudget,
                totalSpent = totalSpent,
                progress = overallProgress
            )
        }

        // ==========================================
        // CATEGORY BUDGETS
        // ==========================================

        item {

            CategoryBudgetSection(
                budgets = budgets,
                categoryMap = categoryMap,
                categoryExpenses = categoryExpenses,
                onDelete = { budget ->
                    budgetViewModel.deleteBudget(budget)
                }
            )
        }

        // ==========================================
        // ADD BUDGET
        // ==========================================

        item {

            Button(
                onClick = {
                    navController.navigate(
                        AppRoutes.AddBudget.route
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Budget",
                    modifier = Modifier.size(21.dp)
                )

                Spacer(
                    modifier = Modifier.size(8.dp)
                )

                Text(
                    text = "Add Budget",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )
        }
    }
}


// ==================================================
// BUDGET SUMMARY CARD
// ==================================================

@Composable
fun BudgetSummaryCard(
    totalBudget: Double,
    totalSpent: Double,
    remaining: Double
) {

    val colorScheme = MaterialTheme.colorScheme

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "Monthly Budget",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = colorScheme.onPrimaryContainer
                    )

                    Spacer(
                        modifier = Modifier.height(5.dp)
                    )

                    Text(
                        text = "৳ ${"%.2f".format(totalBudget)}",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.onPrimaryContainer
                    )
                }

                Row(
                    modifier = Modifier
                        .size(50.dp)
                        .background(
                            color = colorScheme.primary.copy(
                                alpha = 0.15f
                            ),
                            shape = CircleShape
                        ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.AccountBalanceWallet,
                        contentDescription = "Budget",
                        tint = colorScheme.primary,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                SummaryAmount(
                    label = "Spent",
                    amount = "৳ ${"%.2f".format(totalSpent)}",
                    color = colorScheme.error
                )

                SummaryAmount(
                    label = "Remaining",
                    amount = if (remaining < 0) {
                        "-৳ ${"%.2f".format(
                            kotlin.math.abs(remaining)
                        )}"
                    } else {
                        "৳ ${"%.2f".format(remaining)}"
                    },
                    color = if (remaining < 0) {
                        colorScheme.error
                    } else {
                        colorScheme.primary
                    },
                    alignEnd = true
                )
            }
        }
    }
}


// ==================================================
// SUMMARY AMOUNT
// ==================================================

@Composable
private fun SummaryAmount(
    label: String,
    amount: String,
    color: androidx.compose.ui.graphics.Color,
    alignEnd: Boolean = false
) {

    Column(
        horizontalAlignment = if (alignEnd) {
            Alignment.End
        } else {
            Alignment.Start
        }
    ) {

        Text(
            text = label,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = amount,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}


// ==================================================
// BUDGET OVERVIEW
// ==================================================

@Composable
fun BudgetOverviewSection(
    totalBudget: Double,
    totalSpent: Double,
    progress: Float
) {

    val colorScheme = MaterialTheme.colorScheme

    val percentage =
        if (totalBudget > 0) {
            ((totalSpent / totalBudget) * 100)
                .toInt()
        } else {
            0
        }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Budget Overview",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.TrendingDown,
                            contentDescription = "Spending",
                            tint = colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(
                            modifier = Modifier.size(8.dp)
                        )

                        Text(
                            text = "Monthly Spending",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = colorScheme.onSurface
                        )
                    }

                    Text(
                        text = "$percentage% Used",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (percentage >= 100) {
                            colorScheme.error
                        } else {
                            colorScheme.primary
                        }
                    )
                }

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                LinearProgressIndicator(
                    progress = {
                        progress.coerceIn(0f, 1f)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp),
                    color = if (percentage >= 100) {
                        colorScheme.error
                    } else {
                        colorScheme.primary
                    },
                    trackColor = colorScheme.surfaceVariant,
                    strokeCap = StrokeCap.Round
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = "৳ ${"%.2f".format(totalSpent)} of ৳ ${"%.2f".format(totalBudget)} spent",
                    fontSize = 13.sp,
                    color = colorScheme.onSurfaceVariant
                )
            }
        }
    }
}


// ==================================================
// CATEGORY BUDGET SECTION
// ==================================================

@Composable
fun CategoryBudgetSection(
    budgets: List<BudgetEntity>,
    categoryMap: Map<Long, CategoryEntity>,
    categoryExpenses: Map<Long, Double>,
    onDelete: (BudgetEntity) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Category Budgets",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        if (budgets.isEmpty()) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.surface
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.AccountBalanceWallet,
                        contentDescription = "No budgets",
                        tint =
                            MaterialTheme.colorScheme
                                .onSurfaceVariant,
                        modifier = Modifier.size(38.dp)
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Text(
                        text = "No budgets added yet.",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color =
                            MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "Create a budget to start tracking your spending.",
                        fontSize = 13.sp,
                        color =
                            MaterialTheme.colorScheme
                                .onSurfaceVariant
                    )
                }
            }

        } else {

            budgets.forEach { budget ->

                val categoryName =
                    categoryMap[budget.categoryId]?.name
                        ?: "Unknown Category"

                val spentAmount =
                    categoryExpenses[budget.categoryId]
                        ?: 0.0

                val progress =
                    if (budget.amount > 0) {
                        (spentAmount / budget.amount)
                            .coerceIn(0.0, 1.0)
                            .toFloat()
                    } else {
                        0f
                    }

                val exceededAmount =
                    if (spentAmount > budget.amount) {
                        spentAmount - budget.amount
                    } else {
                        0.0
                    }

                BudgetCategoryItem(
                    categoryName = categoryName,
                    spentAmount =
                        "৳ ${"%.2f".format(spentAmount)}",
                    budgetAmount =
                        "৳ ${"%.2f".format(budget.amount)}",
                    progress = progress,
                    exceededAmount = exceededAmount,
                    onDelete = {
                        onDelete(budget)
                    }
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }
        }
    }
}


// ==================================================
// CATEGORY BUDGET ITEM
// ==================================================

@Composable
fun BudgetCategoryItem(
    categoryName: String,
    spentAmount: String,
    budgetAmount: String,
    progress: Float,
    exceededAmount: Double,
    onDelete: () -> Unit
) {

    val colorScheme = MaterialTheme.colorScheme

    val isExceeded =
        exceededAmount > 0

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(17.dp)
        ) {

            // --------------------------------------
            // CATEGORY + AMOUNT
            // --------------------------------------

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Row(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = if (isExceeded) {
                                    colorScheme.error
                                        .copy(alpha = 0.12f)
                                } else {
                                    colorScheme.primary
                                        .copy(alpha = 0.12f)
                                },
                                shape = CircleShape
                            ),
                        horizontalArrangement =
                            Arrangement.Center,
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector =
                                if (isExceeded) {
                                    Icons.Default.Warning
                                } else {
                                    Icons.Default.AccountBalanceWallet
                                },
                            contentDescription =
                                "Category Budget",
                            tint =
                                if (isExceeded) {
                                    colorScheme.error
                                } else {
                                    colorScheme.primary
                                },
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(
                        modifier = Modifier.size(10.dp)
                    )

                    Column {

                        Text(
                            text = categoryName,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = colorScheme.onSurface
                        )

                        Spacer(
                            modifier = Modifier.height(2.dp)
                        )

                        Text(
                            text = "$spentAmount spent",
                            fontSize = 13.sp,
                            color = colorScheme.onSurfaceVariant
                        )
                    }
                }

                Text(
                    text = budgetAmount,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onSurface
                )
            }

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            // --------------------------------------
            // PROGRESS
            // --------------------------------------

            LinearProgressIndicator(
                progress = {
                    progress.coerceIn(0f, 1f)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = if (isExceeded) {
                    colorScheme.error
                } else {
                    colorScheme.primary
                },
                trackColor = colorScheme.surfaceVariant,
                strokeCap = StrokeCap.Round
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            val percentage =
                if (budgetAmount != "৳ 0.00") {
                    (progress * 100).toInt()
                } else {
                    0
                }

            Text(
                text = "$percentage% used",
                fontSize = 12.sp,
                color = if (isExceeded) {
                    colorScheme.error
                } else {
                    colorScheme.onSurfaceVariant
                }
            )

            // --------------------------------------
            // EXCEEDED WARNING
            // --------------------------------------

            if (isExceeded) {

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Warning,
                        contentDescription =
                            "Budget exceeded",
                        tint =
                            colorScheme.error,
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(
                        modifier = Modifier.size(6.dp)
                    )

                    Text(
                        text =
                            "Budget exceeded by ৳ ${"%.2f".format(exceededAmount)}",
                        color =
                            colorScheme.error,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            // --------------------------------------
            // DELETE BUTTON
            // --------------------------------------

            OutlinedButton(
                onClick = onDelete,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = colorScheme.error
                )
            ) {

                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Budget",
                    modifier = Modifier.size(18.dp)
                )

                Spacer(
                    modifier = Modifier.size(7.dp)
                )

                Text(
                    text = "Delete Budget",
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}