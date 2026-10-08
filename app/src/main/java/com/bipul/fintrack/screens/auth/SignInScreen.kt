package com.bipul.fintrack.screens.auth

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.hilt.navigation.compose.hiltViewModel
import com.bipul.fintrack.R
import com.bipul.fintrack.navigation.AppRoutes
import com.bipul.fintrack.util.security.PasswordHasher
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource

@Composable
fun SignInScreen(
    navController: NavHostController
) {

    val viewModel: UserViewModel = hiltViewModel()

    var emailOrUsername by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var rememberMe by remember {
        mutableStateOf(false)
    }

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = 20.dp,
                vertical = 24.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ==========================================
        // LOGO
        // ==========================================

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Image(
            painter = painterResource(
                id = R.drawable.fintrack_logo
            ),
            contentDescription = "FinTrack Logo",
            modifier = Modifier.size(82.dp)
        )

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        // ==========================================
        // TITLE
        // ==========================================

        Text(
            text = "Welcome Back",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = "Sign in to continue to FinTrack",
            fontSize = 14.sp,
            color = colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        // ==========================================
        // LOGIN CARD
        // ==========================================

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
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
                    .padding(20.dp)
            ) {

                // ==========================================
                // EMAIL / USERNAME
                // ==========================================

                Text(
                    text = "Email or Username",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorScheme.onSurface
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                OutlinedTextField(
                    value = emailOrUsername,
                    onValueChange = {
                        emailOrUsername = it
                        errorMessage = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Enter email or username"
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector =
                                if (emailOrUsername.contains("@")) {
                                    Icons.Default.Email
                                } else {
                                    Icons.Default.Person
                                },
                            contentDescription = "Account"
                        )
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorScheme.primary,
                        unfocusedBorderColor = colorScheme.outline,
                        focusedTextColor = colorScheme.onSurface,
                        unfocusedTextColor = colorScheme.onSurface,
                        focusedPlaceholderColor =
                            colorScheme.onSurfaceVariant,
                        unfocusedPlaceholderColor =
                            colorScheme.onSurfaceVariant,
                        focusedLeadingIconColor =
                            colorScheme.primary,
                        unfocusedLeadingIconColor =
                            colorScheme.onSurfaceVariant
                    )
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                // ==========================================
                // PASSWORD
                // ==========================================

                Text(
                    text = "Password",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorScheme.onSurface
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        errorMessage = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Enter your password"
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Password"
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                passwordVisible = !passwordVisible
                            }
                        ) {
                            Icon(
                                imageVector =
                                    if (passwordVisible) {
                                        Icons.Default.VisibilityOff
                                    } else {
                                        Icons.Default.Visibility
                                    },
                                contentDescription =
                                    if (passwordVisible) {
                                        "Hide password"
                                    } else {
                                        "Show password"
                                    }
                            )
                        }
                    },
                    visualTransformation =
                        if (passwordVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorScheme.primary,
                        unfocusedBorderColor = colorScheme.outline,
                        focusedTextColor = colorScheme.onSurface,
                        unfocusedTextColor = colorScheme.onSurface,
                        focusedPlaceholderColor =
                            colorScheme.onSurfaceVariant,
                        unfocusedPlaceholderColor =
                            colorScheme.onSurfaceVariant,
                        focusedLeadingIconColor =
                            colorScheme.primary,
                        unfocusedLeadingIconColor =
                            colorScheme.onSurfaceVariant,
                        focusedTrailingIconColor =
                            colorScheme.primary,
                        unfocusedTrailingIconColor =
                            colorScheme.onSurfaceVariant
                    )
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                // ==========================================
                // REMEMBER + FORGOT
                // ==========================================

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement =
                        Arrangement.SpaceBetween
                ) {

                    Row(
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Checkbox(
                            checked = rememberMe,
                            onCheckedChange = {
                                rememberMe = it
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor =
                                    colorScheme.primary,
                                checkmarkColor =
                                    colorScheme.onPrimary,
                                uncheckedColor =
                                    colorScheme.outline
                            )
                        )

                        Text(
                            text = "Remember me",
                            fontSize = 13.sp,
                            color = colorScheme.onSurfaceVariant
                        )
                    }

                    androidx.compose.material3.TextButton(
                        onClick = {
                            navController.navigate(
                                AppRoutes.ForgotPassword.route
                            )
                        }
                    ) {
                        Text(
                            text = "Forgot Password?",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = colorScheme.primary
                        )
                    }
                }

                // ==========================================
                // ERROR
                // ==========================================

                if (errorMessage.isNotBlank()) {

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text = errorMessage,
                        fontSize = 13.sp,
                        color = colorScheme.error,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                // ==========================================
                // SIGN IN BUTTON
                // ==========================================

                Button(
                    onClick = {

                        if (
                            emailOrUsername.isBlank() ||
                            password.isBlank()
                        ) {
                            errorMessage =
                                "Please enter your email/username and password."
                            return@Button
                        }

                        val passwordHash =
                            PasswordHasher.hash(password)

                        viewModel.loginUser(
                            emailOrUsername = emailOrUsername.trim(),
                            passwordHash = passwordHash,
                            rememberMe = rememberMe
                        ) { user ->

                            if (user != null) {

                                navController.navigate(
                                    AppRoutes.Home.route
                                ) {
                                    popUpTo(
                                        AppRoutes.SignIn.route
                                    ) {
                                        inclusive = true
                                    }
                                }

                            } else {

                                errorMessage =
                                    "Invalid username/email or password."
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.primary,
                        contentColor = colorScheme.onPrimary
                    )
                ) {

                    Text(
                        text = "Sign In",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(22.dp)
        )

        // ==========================================
        // SIGN UP FOOTER
        // ==========================================

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Don't have an account?",
                fontSize = 14.sp,
                color = colorScheme.onSurfaceVariant
            )

            androidx.compose.material3.TextButton(
                onClick = {
                    navController.navigate(
                        AppRoutes.SignUp.route
                    )
                }
            ) {

                Text(
                    text = "Create Account",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.primary
                )
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )
    }
}