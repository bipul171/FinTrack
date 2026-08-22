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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bipul.fintrack.navigation.AppRoutes


@Composable
fun BudgetScreen(navController: NavHostController) {

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
            BudgetSummaryCard()
        }

        item {
            BudgetOverviewSection()
        }

        item {
            CategoryBudgetSection()
        }

        item {
            Button(
                onClick = {
                    navController.navigate(AppRoutes.AddBudget.route)
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
fun BudgetSummaryCard() {

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
                text = "৳ 20,000",
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
                        text = "৳ 15,000",
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
                        text = "৳ 5,000",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


@Composable
fun BudgetOverviewSection() {

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
                        text = "75% Used",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                LinearProgressIndicator(
                    progress = { 0.75f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp),
                    strokeCap = StrokeCap.Round
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = "৳ 15,000 of ৳ 20,000 spent",
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun CategoryBudgetSection() {

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

        BudgetCategoryItem(
            categoryName = "Food",
            spentAmount = "৳ 5,000",
            budgetAmount = "৳ 8,000",
            progress = 0.625f
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        BudgetCategoryItem(
            categoryName = "Transport",
            spentAmount = "৳ 2,000",
            budgetAmount = "৳ 3,000",
            progress = 0.67f
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        BudgetCategoryItem(
            categoryName = "Entertainment",
            spentAmount = "৳ 1,500",
            budgetAmount = "৳ 4,000",
            progress = 0.375f
        )
    }
}


@Composable
fun BudgetCategoryItem(
    categoryName: String,
    spentAmount: String,
    budgetAmount: String,
    progress: Float
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
        }
    }
}

