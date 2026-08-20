package com.bipul.fintrack.screens.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bipul.fintrack.ui.theme.FinTrackPrimary
import com.bipul.fintrack.ui.theme.ScreenBackground

@Composable
fun ForgotPasswordScreen(onBackClick: () -> Unit) {

    var email by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        containerColor = ScreenBackground

    ) {innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Button(
                    onClick = onBackClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Arrow Back"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(com.bipul.fintrack.R.drawable.fintrack_logo),
                contentDescription = "Logo",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Forgot Password?",
                fontSize = 30.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Enter your email address and we'll send " +
                        "you a link to reset your password.",
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email"
                    )
                },
                label = {
                    Text(
                        text = "Email",
                        fontSize = 16.sp
                    )
                },
                modifier = Modifier.fillMaxWidth()
                    .padding(all =16.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(16.dp)
            )


            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    Toast.makeText(
                        context,
                        "Password reset link sent to $email",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = FinTrackPrimary,
                    contentColor = Color.White
                )
            ){
                Text(
                    text = "Send Reset Link",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = onBackClick
            ) {
                Text(
                    text = "Back to Sign In",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = FinTrackPrimary
                )
            }
        }


    }

}