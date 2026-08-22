package com.bipul.fintrack.screens.budget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
fun AddBudgetScreen(
    navController: NavHostController
) {

    var selectedCategory by remember {
        mutableStateOf("")
    }

    var budgetAmount by remember {
        mutableStateOf("")
    }

    var selectedMonth by remember {
        mutableStateOf("August 2026")
    }

    var categoryExpanded by remember {
        mutableStateOf(false)
    }

    var monthExpanded by remember {
        mutableStateOf(false)
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    val categories = listOf(
        "Food",
        "Transport",
        "Entertainment",
        "Shopping",
        "Bills",
        "Others"
    )

    val months = listOf(
        "August 2026",
        "September 2026",
        "October 2026",
        "November 2026",
        "December 2026"
    )

    Scaffold { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }

                Text(
                    text = "Add Budget",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.height(24.dp)
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

                    Text(
                        text = "Create a new budget",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = "Set a spending limit for a category.",
                        fontSize = 14.sp
                    )

                    Spacer(
                        modifier = Modifier.height(24.dp)
                    )

                    Text(
                        text = "Category",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        OutlinedTextField(
                            value = selectedCategory,
                            onValueChange = {},
                            readOnly = true,
                            placeholder = {
                                Text(
                                    text = "Select category"
                                )
                            },
                            trailingIcon = {
                                Text(
                                    text = "▼",
                                    modifier = Modifier.padding(end = 12.dp)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    categoryExpanded = true
                                },
                            singleLine = true
                        )

                        DropdownMenu(
                            expanded = categoryExpanded,
                            onDismissRequest = {
                                categoryExpanded = false
                            }
                        ) {

                            categories.forEach { category ->

                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = category
                                        )
                                    },
                                    onClick = {
                                        selectedCategory = category
                                        categoryExpanded = false
                                        errorMessage = ""
                                    }
                                )
                            }
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    Text(
                        text = "Budget Amount",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    OutlinedTextField(
                        value = budgetAmount,
                        onValueChange = {
                            budgetAmount = it
                            errorMessage = ""
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                text = "Enter budget amount"
                            )
                        },
                        prefix = {
                            Text(
                                text = "৳ "
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        singleLine = true
                    )

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    Text(
                        text = "Month",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        OutlinedTextField(
                            value = selectedMonth,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                Text(
                                    text = "▼",
                                    modifier = Modifier.padding(end = 12.dp)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    monthExpanded = true
                                },
                            singleLine = true
                        )

                        DropdownMenu(
                            expanded = monthExpanded,
                            onDismissRequest = {
                                monthExpanded = false
                            }
                        ) {

                            months.forEach { month ->

                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = month
                                        )
                                    },
                                    onClick = {
                                        selectedMonth = month
                                        monthExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    // Validation message
                    if (errorMessage.isNotEmpty()) {

                        Text(
                            text = errorMessage,
                            fontSize = 14.sp
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    Button(
                        onClick = {

                            when {

                                selectedCategory.isEmpty() -> {
                                    errorMessage =
                                        "Please select a category."
                                }

                                budgetAmount.isEmpty() -> {
                                    errorMessage =
                                        "Please enter a budget amount."
                                }

                                budgetAmount.toDoubleOrNull() == null -> {
                                    errorMessage =
                                        "Please enter a valid amount."
                                }

                                budgetAmount.toDouble() <= 0 -> {
                                    errorMessage =
                                        "Budget amount must be greater than 0."
                                }

                                else -> {
                                    /*
                                     * Database integration will be added later.
                                     *
                                     * For now, simply return to
                                     * the Budget screen.
                                     */
                                    navController.popBackStack()
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {

                        Text(
                            text = "Save Budget",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun AddBudgetScreenPreview() {

    AddBudgetScreen(
        navController = rememberNavController()
    )
}