package com.bipul.fintrack.screens.budget


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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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

    val totalBudget = budgets.sumOf {
        it.amount
    }

    val totalSpent = categoryExpenses.values.sum()

    val remaining = totalBudget - totalSpent

    val overallProgress =
        if (totalBudget > 0) {
            (totalSpent / totalBudget).toFloat()
        } else {
            0f
        }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {

            Text(
                text = "Budget",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {

            BudgetSummaryCard(
                totalBudget = totalBudget,
                totalSpent = totalSpent,
                remaining = remaining
            )
        }

        item {

            BudgetOverviewSection(
                totalBudget = totalBudget,
                totalSpent = totalSpent,
                progress = overallProgress
            )
        }

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

        item {

            Button(
                onClick = {
                    navController.navigate(
                        AppRoutes.AddBudget.route
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "+ Add Budget"
                )
            }
        }
    }
}


@Composable
fun BudgetSummaryCard(
    totalBudget: Double,
    totalSpent: Double,
    remaining: Double
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            Text(
                text = "Monthly Budget",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "৳ ${"%.2f".format(totalBudget)}",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {

                    Text(
                        text = "Spent",
                        fontSize = 14.sp
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "৳ ${"%.2f".format(totalSpent)}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {

                    Text(
                        text = "Remaining",
                        fontSize = 14.sp
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = if (remaining < 0) {
                            "-৳ ${"%.2f".format(kotlin.math.abs(remaining))}"
                        } else {
                            "৳ ${"%.2f".format(remaining)}"
                        },
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (remaining < 0) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun BudgetOverviewSection(
    totalBudget: Double,
    totalSpent: Double,
    progress: Float
) {

    val percentage = (progress * 100).toInt()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Budget Overview",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Monthly Spending",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = "$percentage% Used",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                LinearProgressIndicator(
                    progress = { progress.coerceIn(0f, 1f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp),
                    strokeCap = StrokeCap.Round
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = "৳ ${"%.2f".format(totalSpent)} of ৳ ${"%.2f".format(totalBudget)} spent",
                    fontSize = 14.sp
                )
            }
        }
    }
}


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
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        if (budgets.isEmpty()) {

            Text(
                text = "No budgets added yet.",
                fontSize = 15.sp
            )

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
                    spentAmount = "৳ ${"%.2f".format(spentAmount)}",
                    budgetAmount = "৳ ${"%.2f".format(budget.amount)}",
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


@Composable
fun BudgetCategoryItem(
    categoryName: String,
    spentAmount: String,
    budgetAmount: String,
    progress: Float,
    exceededAmount: Double,
    onDelete: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = categoryName,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "$spentAmount / $budgetAmount",
                    fontSize = 14.sp
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                strokeCap = StrokeCap.Round
            )

            if (exceededAmount > 0) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "⚠ Budget exceeded by ৳ ${"%.2f".format(exceededAmount)}",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Button(
                onClick = onDelete,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "Delete Budget"
                )
            }
        }
    }
}
