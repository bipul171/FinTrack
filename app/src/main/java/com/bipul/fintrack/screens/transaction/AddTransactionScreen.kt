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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun AddTransactionScreen(
    navHostController: NavHostController
) {

    val context = LocalContext.current
    var amount by remember {
        mutableStateOf("")
    }

    var note by remember {
        mutableStateOf("")
    }

    var transactionType by remember {
        mutableStateOf("Expense")
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
                Toast.makeText(
                    context,
                    "Transaction saved successfully",
                    Toast.LENGTH_SHORT
                ).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Transaction")
        }
    }
}