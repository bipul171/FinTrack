package com.bipul.fintrack.screens.transaction

import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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

    var customCategory by remember {
        mutableStateOf("")
    }

    var categoryExpanded by remember {
        mutableStateOf(false)
    }

    val expenseCategories = categories
        .filter {
            it.type.equals(
                "Expense",
                ignoreCase = true
            )
        }
        .sortedWith(
            compareBy {
                it.name.equals(
                    "Others",
                    ignoreCase = true
                )
            }
        )

    // --------------------------------------
    // Theme Colors
    // --------------------------------------

    val colorScheme = MaterialTheme.colorScheme

    val expenseColor = Color(0xFFC95757)
    val expenseContainer = Color(0xFFFBE7E7)

    val incomeColor = Color(0xFF3F8F5B)
    val incomeContainer = Color(0xFFE3F3E7)

    val isDarkTheme = colorScheme.background.luminance() < 0.5f

    val expenseButtonContainer =
        if (isDarkTheme) {
            Color(0xFF5C3030)
        } else {
            expenseContainer
        }

    val incomeButtonContainer =
        if (isDarkTheme) {
            Color(0xFF294D33)
        } else {
            incomeContainer
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
            .verticalScroll(
                rememberScrollState()
            )
            .padding(
                horizontal = 20.dp,
                vertical = 18.dp
            ),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        // ======================================
        // HEADER
        // ======================================

        Text(
            text = "Add Transaction",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = colorScheme.onBackground
        )

        Text(
            text = "Record your income or expense",
            fontSize = 14.sp,
            color = colorScheme.onSurfaceVariant
        )

        // ======================================
        // TRANSACTION TYPE CARD
        // ======================================

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            )
        ) {

            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                Text(
                    text = "Transaction Type",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorScheme.onSurface
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    // ----------------------------------
                    // EXPENSE BUTTON
                    // ----------------------------------

                    if (transactionType == "Expense") {

                        Button(
                            onClick = {
                                transactionType = "Expense"
                                selectedCategory = ""
                                selectedCategoryId = null
                                customCategory = ""
                                categoryExpanded = false
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = expenseColor,
                                contentColor = Color.White
                            )
                        ) {

                            Text(
                                text = "Expense",
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                    } else {

                        OutlinedButton(
                            onClick = {
                                transactionType = "Expense"
                                selectedCategory = ""
                                selectedCategoryId = null
                                customCategory = ""
                                categoryExpanded = false
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = expenseButtonContainer,
                                contentColor = expenseColor
                            )
                        ) {

                            Text(
                                text = "Expense",
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    // ----------------------------------
                    // INCOME BUTTON
                    // ----------------------------------

                    if (transactionType == "Income") {

                        Button(
                            onClick = {
                                transactionType = "Income"
                                selectedCategory = ""
                                selectedCategoryId = null
                                customCategory = ""
                                categoryExpanded = false
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = incomeColor,
                                contentColor = Color.White
                            )
                        ) {

                            Text(
                                text = "Income",
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                    } else {

                        OutlinedButton(
                            onClick = {
                                transactionType = "Income"
                                selectedCategory = ""
                                selectedCategoryId = null
                                customCategory = ""
                                categoryExpanded = false
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = incomeButtonContainer,
                                contentColor = incomeColor
                            )
                        ) {

                            Text(
                                text = "Income",
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }

        // ======================================
        // AMOUNT
        // ======================================

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
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Payments,
                    contentDescription = "Amount",
                    modifier = Modifier.size(21.dp)
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorScheme.primary,
                unfocusedBorderColor = colorScheme.outline,
                focusedLabelColor = colorScheme.primary,
                unfocusedLabelColor = colorScheme.onSurfaceVariant,
                cursorColor = colorScheme.primary,
                focusedLeadingIconColor = colorScheme.primary,
                unfocusedLeadingIconColor = colorScheme.onSurfaceVariant,
                focusedTextColor = colorScheme.onSurface,
                unfocusedTextColor = colorScheme.onSurface,
                focusedPlaceholderColor = colorScheme.onSurfaceVariant,
                unfocusedPlaceholderColor = colorScheme.onSurfaceVariant
            ),
            singleLine = true
        )

        // ======================================
        // EXPENSE CATEGORY
        // ======================================

        if (transactionType == "Expense") {

            Text(
                text = "Category",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorScheme.onBackground
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
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Category,
                            contentDescription = "Category",
                            modifier = Modifier.size(21.dp)
                        )
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = categoryExpanded
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorScheme.primary,
                        unfocusedBorderColor = colorScheme.outline,
                        focusedLabelColor = colorScheme.primary,
                        focusedLeadingIconColor = colorScheme.primary,
                        unfocusedLeadingIconColor = colorScheme.onSurfaceVariant,
                        focusedTextColor = colorScheme.onSurface,
                        unfocusedTextColor = colorScheme.onSurface,
                        focusedPlaceholderColor = colorScheme.onSurfaceVariant,
                        unfocusedPlaceholderColor = colorScheme.onSurfaceVariant
                    ),
                    singleLine = true
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
                                    text = category.name,
                                    color = colorScheme.onSurface
                                )
                            },
                            onClick = {

                                selectedCategory =
                                    category.name

                                selectedCategoryId =
                                    category.categoryId

                                customCategory = ""

                                categoryExpanded = false
                            }
                        )
                    }
                }
            }

            // ==================================
            // CUSTOM CATEGORY
            // ==================================

            if (
                selectedCategory.equals(
                    "Others",
                    ignoreCase = true
                )
            ) {

                OutlinedTextField(
                    value = customCategory,
                    onValueChange = {
                        customCategory = it
                        selectedCategoryId = null
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Custom Category")
                    },
                    placeholder = {
                        Text("e.g. Medical")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Category,
                            contentDescription = "Custom Category",
                            modifier = Modifier.size(21.dp)
                        )
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = expenseColor,
                        unfocusedBorderColor = colorScheme.outline,
                        focusedLabelColor = expenseColor,
                        focusedLeadingIconColor = expenseColor,
                        unfocusedLeadingIconColor = colorScheme.onSurfaceVariant,
                        focusedTextColor = colorScheme.onSurface,
                        unfocusedTextColor = colorScheme.onSurface,
                        focusedPlaceholderColor = colorScheme.onSurfaceVariant,
                        unfocusedPlaceholderColor = colorScheme.onSurfaceVariant,
                        cursorColor = expenseColor
                    ),
                    singleLine = true
                )
            }
        }

        // ======================================
        // INCOME CATEGORY
        // ======================================

        if (transactionType == "Income") {

            Text(
                text = "Income Category",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorScheme.onBackground
            )

            OutlinedTextField(
                value = selectedCategory,
                onValueChange = {
                    selectedCategory = it
                    selectedCategoryId = null
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Category")
                },
                placeholder = {
                    Text("e.g. Tuition, Salary, Freelance")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Category,
                        contentDescription = "Income Category",
                        modifier = Modifier.size(21.dp)
                    )
                },
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = incomeColor,
                    unfocusedBorderColor = colorScheme.outline,
                    focusedLabelColor = incomeColor,
                    focusedLeadingIconColor = incomeColor,
                    unfocusedLeadingIconColor = colorScheme.onSurfaceVariant,
                    focusedTextColor = colorScheme.onSurface,
                    unfocusedTextColor = colorScheme.onSurface,
                    focusedPlaceholderColor = colorScheme.onSurfaceVariant,
                    unfocusedPlaceholderColor = colorScheme.onSurfaceVariant,
                    cursorColor = incomeColor
                ),
                singleLine = true
            )
        }

        // ======================================
        // NOTE
        // ======================================

        OutlinedTextField(
            value = note,
            onValueChange = {
                note = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(105.dp),
            label = {
                Text("Note")
            },
            placeholder = {
                Text("Optional note")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Description,
                    contentDescription = "Note",
                    modifier = Modifier.size(21.dp)
                )
            },
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorScheme.primary,
                unfocusedBorderColor = colorScheme.outline,
                focusedLabelColor = colorScheme.primary,
                unfocusedLabelColor = colorScheme.onSurfaceVariant,
                focusedLeadingIconColor = colorScheme.primary,
                unfocusedLeadingIconColor = colorScheme.onSurfaceVariant,
                focusedTextColor = colorScheme.onSurface,
                unfocusedTextColor = colorScheme.onSurface,
                focusedPlaceholderColor = colorScheme.onSurfaceVariant,
                unfocusedPlaceholderColor = colorScheme.onSurfaceVariant,
                cursorColor = colorScheme.primary
            ),
            maxLines = 3
        )

        Spacer(
            modifier = Modifier.height(2.dp)
        )

        // ======================================
        // SAVE BUTTON
        // ======================================

        Button(
            onClick = {

                val amountValue =
                    amount.toDoubleOrNull()

                if (
                    amountValue == null ||
                    amountValue <= 0
                ) {

                    Toast.makeText(
                        context,
                        "Please enter a valid amount",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@Button
                }

                // ----------------------------------
                // INCOME
                // ----------------------------------

                if (transactionType == "Income") {

                    val incomeCategory =
                        selectedCategory.trim()

                    if (incomeCategory.isEmpty()) {

                        Toast.makeText(
                            context,
                            "Please enter an income category",
                            Toast.LENGTH_SHORT
                        ).show()

                        return@Button
                    }

                    categoryViewModel.findOrCreateCategory(
                        name = incomeCategory,
                        type = "Income"
                    ) { categoryId ->

                        viewModel.addTransaction(
                            amount = amountValue,
                            transactionType = "Income",
                            note = note.trim(),
                            categoryId = categoryId
                        )

                        Toast.makeText(
                            context,
                            "Income saved successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        navHostController.popBackStack()
                    }

                    return@Button
                }

                // ----------------------------------
                // EXPENSE
                // ----------------------------------

                if (selectedCategory.isBlank()) {

                    Toast.makeText(
                        context,
                        "Please select a category",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@Button
                }

                // ----------------------------------
                // OTHERS
                // ----------------------------------

                if (
                    selectedCategory.equals(
                        "Others",
                        ignoreCase = true
                    )
                ) {

                    val customName =
                        customCategory.trim()

                    if (customName.isEmpty()) {

                        Toast.makeText(
                            context,
                            "Please enter a custom category",
                            Toast.LENGTH_SHORT
                        ).show()

                        return@Button
                    }

                    categoryViewModel.findOrCreateCategory(
                        name = customName,
                        type = "Expense"
                    ) { categoryId ->

                        viewModel.addTransaction(
                            amount = amountValue,
                            transactionType = "Expense",
                            note = note.trim(),
                            categoryId = categoryId
                        )

                        Toast.makeText(
                            context,
                            "Expense saved successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        navHostController.popBackStack()
                    }

                } else {

                    val categoryId =
                        selectedCategoryId

                    if (categoryId == null) {

                        Toast.makeText(
                            context,
                            "Please select a category",
                            Toast.LENGTH_SHORT
                        ).show()

                        return@Button
                    }

                    viewModel.addTransaction(
                        amount = amountValue,
                        transactionType = "Expense",
                        note = note.trim(),
                        categoryId = categoryId
                    )

                    Toast.makeText(
                        context,
                        "Expense saved successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    navHostController.popBackStack()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.primary,
                contentColor = colorScheme.onPrimary
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 2.dp
            )
        ) {

            Text(
                text = "Save Transaction",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )
    }
}