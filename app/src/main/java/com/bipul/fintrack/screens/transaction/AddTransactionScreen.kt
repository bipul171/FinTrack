package com.bipul.fintrack.screens.transaction

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bipul.fintrack.screens.category.CategoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    navHostController: NavHostController,
    viewModel: TransactionViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val categoryViewModel: CategoryViewModel = hiltViewModel()

    val categories by categoryViewModel.categories.collectAsState(
        initial = emptyList()
    )

    var amount by remember {
        mutableStateOf("")
    }

    var note by remember {
        mutableStateOf("")
    }

    var transactionType by remember {
        mutableStateOf("Expense")
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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Add Transaction",
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            if (transactionType == "Expense") {

                Button(
                    onClick = {
                        transactionType = "Expense"
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Expense")
                }

            } else {

                OutlinedButton(
                    onClick = {
                        transactionType = "Expense"
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Expense")
                }
            }

            if (transactionType == "Income") {

                Button(
                    onClick = {
                        transactionType = "Income"
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Income")
                }

            } else {

                OutlinedButton(
                    onClick = {
                        transactionType = "Income"
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Income")
                }
            }
        }

        Text(
            text = "Selected: $transactionType",
            fontSize = 14.sp
        )

        OutlinedTextField(
            value = amount,
            onValueChange = {
                amount = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Amount")
            },
            placeholder = {
                Text("Enter amount")
            },
            singleLine = true
        )

        if (transactionType == "Expense") {

            Text(
                text = "Category",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )

            ExposedDropdownMenuBox(
                expanded = categoryExpanded,
                onExpandedChange = {
                    categoryExpanded = !categoryExpanded
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                OutlinedTextField(
                    value = selectedCategory,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = {
                        Text("Select category")
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = categoryExpanded
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    singleLine = true
                )

                ExposedDropdownMenu(
                    expanded = categoryExpanded,
                    onDismissRequest = {
                        categoryExpanded = false
                    }
                ) {

                    categories
                        .filter { it.type == "Expense" }
                        .forEach { category ->

                            DropdownMenuItem(
                                text = {
                                    Text(category.name)
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

        OutlinedTextField(
            value = note,
            onValueChange = {
                note = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Note")
            },
            placeholder = {
                Text("Optional note")
            }
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Button(
            onClick = {

                val amountValue =
                    amount.toDoubleOrNull()

                if (amountValue == null || amountValue <= 0) {

                    Toast.makeText(
                        context,
                        "Please enter a valid amount",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@Button
                }

                if (
                    transactionType == "Expense" &&
                    selectedCategoryId == null
                ) {

                    Toast.makeText(
                        context,
                        "Please select a category",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@Button
                }

                viewModel.addTransaction(
                    amount = amountValue,
                    transactionType = transactionType,
                    note = note,
                    categoryId = selectedCategoryId
                )

                Toast.makeText(
                    context,
                    "Transaction saved successfully",
                    Toast.LENGTH_SHORT
                ).show()

                navHostController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Save Transaction")
        }
    }
}