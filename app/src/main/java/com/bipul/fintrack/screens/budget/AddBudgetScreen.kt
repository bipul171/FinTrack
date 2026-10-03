package com.bipul.fintrack.screens.budget

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bipul.fintrack.screens.category.CategoryViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun AddBudgetScreen(
    navController: NavHostController
) {

    val context = LocalContext.current

    val categoryViewModel: CategoryViewModel = hiltViewModel()
    val budgetViewModel: BudgetViewModel = hiltViewModel()

    // ---------------------------------------------------------
    // CATEGORIES
    // ---------------------------------------------------------

    val categories by categoryViewModel.categories.collectAsState(
        initial = emptyList()
    )

    /*
     * Budget শুধুমাত্র Expense category-এর জন্য।
     *
     * Others সবসময় একদম নিচে থাকবে।
     */
    val expenseCategories = remember(categories) {

        val normalCategories = categories
            .filter {
                it.type.equals("Expense", ignoreCase = true)
            }
            .filter {
                !it.name.equals("Others", ignoreCase = true)
            }
            .sortedBy {
                it.name.lowercase()
            }

        val othersCategory = categories.firstOrNull {
            it.type.equals("Expense", ignoreCase = true) &&
                    it.name.equals("Others", ignoreCase = true)
        }

        if (othersCategory != null) {
            normalCategories + othersCategory
        } else {
            normalCategories
        }
    }

    // ---------------------------------------------------------
    // STATE
    // ---------------------------------------------------------

    var selectedCategory by remember {
        mutableStateOf("")
    }

    var selectedCategoryId by remember {
        mutableStateOf<Long?>(null)
    }

    var customCategory by remember {
        mutableStateOf("")
    }

    var budgetAmount by remember {
        mutableStateOf("")
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

    // ---------------------------------------------------------
    // DYNAMIC MONTH LIST
    // Current month -> December of current year
    // ---------------------------------------------------------

    val currentDate = remember {
        Calendar.getInstance()
    }

    val currentMonthIndex = currentDate.get(Calendar.MONTH)
    val currentYear = currentDate.get(Calendar.YEAR)

    val monthFormatter = remember {
        SimpleDateFormat(
            "MMMM yyyy",
            Locale.ENGLISH
        )
    }

    val months = remember(
        currentMonthIndex,
        currentYear
    ) {

        val result = mutableListOf<String>()

        for (monthIndex in currentMonthIndex..Calendar.DECEMBER) {

            val calendar = Calendar.getInstance()

            calendar.set(
                Calendar.YEAR,
                currentYear
            )

            calendar.set(
                Calendar.MONTH,
                monthIndex
            )

            calendar.set(
                Calendar.DAY_OF_MONTH,
                1
            )

            result.add(
                monthFormatter.format(
                    calendar.time
                )
            )
        }

        result
    }

    var selectedMonth by remember {
        mutableStateOf(
            months.firstOrNull() ?: ""
        )
    }

    val isOthersSelected =
        selectedCategory.equals(
            "Others",
            ignoreCase = true
        )

    // ---------------------------------------------------------
    // SCREEN
    // ---------------------------------------------------------

    Scaffold { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colorScheme.background
                )
                .padding(paddingValues)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                // -------------------------------------------------
                // TOP BAR
                // -------------------------------------------------

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme
                                .colorScheme
                                .onBackground
                        )
                    }

                    Spacer(
                        modifier = Modifier.width(4.dp)
                    )

                    Column {

                        Text(
                            text = "Add Budget",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme
                                .colorScheme
                                .onBackground
                        )

                        Text(
                            text = "Set your monthly spending limit",
                            fontSize = 13.sp,
                            color = MaterialTheme
                                .colorScheme
                                .onSurfaceVariant
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                // -------------------------------------------------
                // MAIN CARD
                // -------------------------------------------------

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(
                        containerColor =
                            MaterialTheme.colorScheme.surface
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

                        Text(
                            text = "Create a new budget",
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme
                                .colorScheme
                                .onSurface
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(
                            text =
                                "Choose a category and set your spending limit.",
                            fontSize = 13.sp,
                            color = MaterialTheme
                                .colorScheme
                                .onSurfaceVariant
                        )

                        Spacer(
                            modifier = Modifier.height(24.dp)
                        )

                        // =================================================
                        // CATEGORY
                        // =================================================

                        Text(
                            text = "Category",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme
                                .colorScheme
                                .onSurface
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            OutlinedTextField(
                                value = selectedCategory,
                                onValueChange = {},
                                readOnly = true,
                                placeholder = {
                                    Text(
                                        text = "Select expense category"
                                    )
                                },
                                trailingIcon = {
                                    Icon(
                                        imageVector =
                                            Icons.Default.KeyboardArrowDown,
                                        contentDescription =
                                            "Select category"
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(14.dp),
                                colors =
                                    OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor =
                                            MaterialTheme
                                                .colorScheme
                                                .primary,
                                        unfocusedBorderColor =
                                            MaterialTheme
                                                .colorScheme
                                                .outline
                                    )
                            )

                            /*
                             * পুরো field-এর উপর transparent clickable
                             * layer।
                             *
                             * এইটার কারণেই আগের Category select bug
                             * হবে না।
                             */
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .clickable {
                                        categoryExpanded =
                                            !categoryExpanded
                                    }
                            )

                            DropdownMenu(
                                expanded = categoryExpanded,
                                onDismissRequest = {
                                    categoryExpanded = false
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                if (expenseCategories.isEmpty()) {

                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text =
                                                    "No expense categories available"
                                            )
                                        },
                                        onClick = {
                                            categoryExpanded = false
                                        }
                                    )

                                } else {

                                    expenseCategories.forEach { category ->

                                        DropdownMenuItem(
                                            text = {

                                                Row(
                                                    modifier =
                                                        Modifier.fillMaxWidth(),
                                                    verticalAlignment =
                                                        Alignment.CenterVertically,
                                                    horizontalArrangement =
                                                        Arrangement.SpaceBetween
                                                ) {

                                                    Text(
                                                        text =
                                                            category.name,
                                                        fontSize = 15.sp
                                                    )

                                                    if (
                                                        category.name
                                                            .equals(
                                                                "Others",
                                                                ignoreCase = true
                                                            )
                                                    ) {

                                                        Text(
                                                            text = "Custom",
                                                            fontSize = 11.sp,
                                                            color =
                                                                MaterialTheme
                                                                    .colorScheme
                                                                    .primary,
                                                            fontWeight =
                                                                FontWeight.SemiBold
                                                        )
                                                    }
                                                }
                                            },
                                            onClick = {

                                                selectedCategory =
                                                    category.name

                                                selectedCategoryId =
                                                    category.categoryId

                                                customCategory = ""

                                                categoryExpanded =
                                                    false

                                                errorMessage = ""
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(
                            modifier = Modifier.height(5.dp)
                        )

                        Text(
                            text =
                                "Only expense categories can be used for budgets.",
                            fontSize = 11.sp,
                            color = MaterialTheme
                                .colorScheme
                                .onSurfaceVariant
                        )

                        // =================================================
                        // CUSTOM CATEGORY
                        // =================================================

                        if (isOthersSelected) {

                            Spacer(
                                modifier = Modifier.height(18.dp)
                            )

                            Text(
                                text = "Custom Category",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme
                                    .colorScheme
                                    .onSurface
                            )

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            OutlinedTextField(
                                value = customCategory,
                                onValueChange = {
                                    customCategory = it
                                    errorMessage = ""
                                },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = {
                                    Text(
                                        text =
                                            "e.g. Medical, Travel, Shopping"
                                    )
                                },
                                singleLine = true,
                                shape = RoundedCornerShape(14.dp),
                                colors =
                                    OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor =
                                            MaterialTheme
                                                .colorScheme
                                                .primary,
                                        unfocusedBorderColor =
                                            MaterialTheme
                                                .colorScheme
                                                .outline
                                    )
                            )

                            Spacer(
                                modifier = Modifier.height(5.dp)
                            )

                            Text(
                                text =
                                    "This category will be saved for future use.",
                                fontSize = 11.sp,
                                color = MaterialTheme
                                    .colorScheme
                                    .onSurfaceVariant
                            )
                        }

                        // =================================================
                        // AMOUNT
                        // =================================================

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        Text(
                            text = "Budget Amount",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme
                                .colorScheme
                                .onSurface
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
                            keyboardOptions =
                                KeyboardOptions(
                                    keyboardType =
                                        KeyboardType.Decimal
                                ),
                            singleLine = true,
                            shape = RoundedCornerShape(14.dp),
                            colors =
                                OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor =
                                        MaterialTheme
                                            .colorScheme
                                            .primary,
                                    unfocusedBorderColor =
                                        MaterialTheme
                                            .colorScheme
                                            .outline
                                )
                        )

                        // =================================================
                        // MONTH
                        // =================================================

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        Text(
                            text = "Month",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme
                                .colorScheme
                                .onSurface
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            OutlinedTextField(
                                value = selectedMonth,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = {
                                    Icon(
                                        imageVector =
                                            Icons.Default.KeyboardArrowDown,
                                        contentDescription =
                                            "Select month"
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(14.dp),
                                colors =
                                    OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor =
                                            MaterialTheme
                                                .colorScheme
                                                .primary,
                                        unfocusedBorderColor =
                                            MaterialTheme
                                                .colorScheme
                                                .outline
                                    )
                            )

                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .clickable {
                                        monthExpanded =
                                            !monthExpanded
                                    }
                            )

                            DropdownMenu(
                                expanded = monthExpanded,
                                onDismissRequest = {
                                    monthExpanded = false
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                months.forEach { month ->

                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = month,
                                                fontSize = 15.sp
                                            )
                                        },
                                        onClick = {

                                            selectedMonth = month

                                            monthExpanded = false

                                            errorMessage = ""
                                        }
                                    )
                                }
                            }
                        }

                        // =================================================
                        // ERROR
                        // =================================================

                        if (errorMessage.isNotEmpty()) {

                            Spacer(
                                modifier = Modifier.height(16.dp)
                            )

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor =
                                        MaterialTheme
                                            .colorScheme
                                            .errorContainer
                                )
                            ) {

                                Text(
                                    text = errorMessage,
                                    modifier = Modifier.padding(12.dp),
                                    fontSize = 13.sp,
                                    color =
                                        MaterialTheme
                                            .colorScheme
                                            .onErrorContainer
                                )
                            }
                        }

                        // =================================================
                        // SAVE
                        // =================================================

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        Button(
                            onClick = {

                                // -----------------------------------------
                                // CATEGORY
                                // -----------------------------------------

                                if (selectedCategory.isEmpty()) {

                                    errorMessage =
                                        "Please select a category."

                                    return@Button
                                }

                                // -----------------------------------------
                                // CUSTOM CATEGORY
                                // -----------------------------------------

                                if (
                                    isOthersSelected &&
                                    customCategory
                                        .trim()
                                        .isEmpty()
                                ) {

                                    errorMessage =
                                        "Please enter your custom category."

                                    return@Button
                                }

                                // -----------------------------------------
                                // AMOUNT
                                // -----------------------------------------

                                if (budgetAmount
                                        .trim()
                                        .isEmpty()
                                ) {

                                    errorMessage =
                                        "Please enter a budget amount."

                                    return@Button
                                }

                                val amount =
                                    budgetAmount.toDoubleOrNull()

                                if (amount == null) {

                                    errorMessage =
                                        "Please enter a valid amount."

                                    return@Button
                                }

                                if (amount <= 0) {

                                    errorMessage =
                                        "Budget amount must be greater than 0."

                                    return@Button
                                }

                                // -----------------------------------------
                                // OTHERS -> CUSTOM CATEGORY
                                // -----------------------------------------

                                if (isOthersSelected) {

                                    categoryViewModel.findOrCreateCategory(
                                        name = customCategory.trim(),
                                        type = "Expense"
                                    ) { categoryId ->

                                        budgetViewModel.addBudget(
                                            categoryId = categoryId,
                                            amount = amount,
                                            month = selectedMonth
                                        ) { success ->

                                            if (success) {

                                                Toast.makeText(
                                                    context,
                                                    "Budget added successfully",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                                navController
                                                    .popBackStack()

                                            } else {

                                                errorMessage =
                                                    "A budget already exists for this category and month."
                                            }
                                        }
                                    }

                                } else {

                                    // -------------------------------------
                                    // NORMAL CATEGORY
                                    // -------------------------------------

                                    if (selectedCategoryId == null) {

                                        errorMessage =
                                            "Invalid category."

                                        return@Button
                                    }

                                    budgetViewModel.addBudget(
                                        categoryId =
                                            selectedCategoryId!!,
                                        amount = amount,
                                        month = selectedMonth
                                    ) { success ->

                                        if (success) {

                                            Toast.makeText(
                                                context,
                                                "Budget added successfully",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            navController
                                                .popBackStack()

                                        } else {

                                            errorMessage =
                                                "A budget already exists for this category and month."
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(14.dp)
                        ) {

                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null
                            )

                            Spacer(
                                modifier = Modifier.width(8.dp)
                            )

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
}