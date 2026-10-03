package com.bipul.fintrack.screens.auth

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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bipul.fintrack.R
import com.bipul.fintrack.ui.theme.FinTrackPrimary
import com.bipul.fintrack.ui.theme.ScreenBackground

@Composable
fun ForgotPasswordScreen(
    onBackClick: () -> Unit,
    onPasswordChanged: () -> Unit,
    viewModel: UserViewModel = hiltViewModel()
) {

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var showNewPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }

    var verifiedUserId by remember { mutableStateOf<Long?>(null) }

    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        containerColor = ScreenBackground
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Back Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    onClick = onBackClick
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Logo
            Image(
                painter = painterResource(R.drawable.fintrack_logo),
                contentDescription = "FinTrack Logo",
                modifier = Modifier.size(130.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Title
            Text(
                text = if (verifiedUserId == null) {
                    "Forgot Password?"
                } else {
                    "Create New Password"
                },
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            // =========================
            // ACCOUNT VERIFICATION
            // =========================

            if (verifiedUserId == null) {

                Text(
                    text = "Enter your username and email to verify your account.",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Username
                OutlinedTextField(
                    value = username,
                    onValueChange = {
                        username = it
                        errorMessage = ""
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Username"
                        )
                    },
                    label = {
                        Text("Username")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Email
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        errorMessage = ""
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email"
                        )
                    },
                    label = {
                        Text("Email")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Error Message
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Verify Account
                Button(
                    onClick = {

                        if (username.isBlank()) {
                            errorMessage = "Please enter your username"
                            return@Button
                        }

                        if (email.isBlank()) {
                            errorMessage = "Please enter your email"
                            return@Button
                        }

                        viewModel.verifyAccount(
                            username = username.trim(),
                            email = email.trim()
                        ) { user ->

                            if (user != null) {
                                verifiedUserId = user.userId
                                errorMessage = ""
                            } else {
                                errorMessage =
                                    "Username and email do not match"
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = FinTrackPrimary,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Verify Account",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            } else {

                // =========================
                // CHANGE PASSWORD
                // =========================

                Text(
                    text = "Your account has been verified. Create a new password.",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(20.dp))

                // New Password
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = {
                        newPassword = it
                        errorMessage = ""
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "New Password"
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                showNewPassword = !showNewPassword
                            }
                        ) {
                            Icon(
                                imageVector = if (showNewPassword) {
                                    Icons.Default.VisibilityOff
                                } else {
                                    Icons.Default.Visibility
                                },
                                contentDescription = if (showNewPassword) {
                                    "Hide password"
                                } else {
                                    "Show password"
                                }
                            )
                        }
                    },
                    label = {
                        Text("New Password")
                    },
                    visualTransformation = if (showNewPassword) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Confirm Password
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        errorMessage = ""
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Confirm Password"
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                showConfirmPassword = !showConfirmPassword
                            }
                        ) {
                            Icon(
                                imageVector = if (showConfirmPassword) {
                                    Icons.Default.VisibilityOff
                                } else {
                                    Icons.Default.Visibility
                                },
                                contentDescription = if (showConfirmPassword) {
                                    "Hide password"
                                } else {
                                    "Show password"
                                }
                            )
                        }
                    },
                    label = {
                        Text("Confirm Password")
                    },
                    visualTransformation = if (showConfirmPassword) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Error Message
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Change Password
                Button(
                    onClick = {

                        if (newPassword.length < 6) {
                            errorMessage =
                                "Password must be at least 6 characters"
                            return@Button
                        }

                        if (newPassword != confirmPassword) {
                            errorMessage =
                                "Passwords do not match"
                            return@Button
                        }

                        val userId = verifiedUserId

                        if (userId != null) {

                            viewModel.changePassword(
                                userId = userId,
                                newPassword = newPassword
                            ) {
                                onPasswordChanged()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = FinTrackPrimary,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Change Password",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Back to Sign In
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