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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.imePadding
import androidx.compose.ui.res.painterResource
import com.bipul.fintrack.R
import com.bipul.fintrack.navigation.AppRoutes
import com.bipul.fintrack.util.security.PasswordHasher

@Composable
fun SignUpScreen(
    navController: NavHostController
) {

    val viewModel: UserViewModel = hiltViewModel()

    var fullName by remember {
        mutableStateOf("")
    }

    var username by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var confirmPassword by remember {
        mutableStateOf("")
    }

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    var confirmPasswordVisible by remember {
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
            .imePadding()
            .padding(
                horizontal = 20.dp,
                vertical = 24.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        // ==========================================
        // LOGO
        // ==========================================

        Image(
            painter = painterResource(
                id = R.drawable.fintrack_logo
            ),
            contentDescription = "FinTrack Logo",
            modifier = Modifier.size(76.dp)
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // ==========================================
        // TITLE
        // ==========================================

        Text(
            text = "Create Account",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = "Create your FinTrack account",
            fontSize = 14.sp,
            color = colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(22.dp)
        )

        // ==========================================
        // SIGN UP CARD
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
                // FULL NAME
                // ==========================================

                Text(
                    text = "Full Name",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorScheme.onSurface
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                OutlinedTextField(
                    value = fullName,
                    onValueChange = {
                        fullName = it
                        errorMessage = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = {
                        Text("Enter your full name")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Full Name"
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
                    modifier = Modifier.height(15.dp)
                )

                // ==========================================
                // USERNAME
                // ==========================================

                Text(
                    text = "Username",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorScheme.onSurface
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                OutlinedTextField(
                    value = username,
                    onValueChange = {
                        username = it
                        errorMessage = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = {
                        Text("Choose a username")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Username"
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
                    modifier = Modifier.height(15.dp)
                )

                // ==========================================
                // EMAIL
                // ==========================================

                Text(
                    text = "Email",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorScheme.onSurface
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        errorMessage = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = {
                        Text("Enter your email")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email"
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
                    modifier = Modifier.height(15.dp)
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
                        Text("Create a password")
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
                                passwordVisible =
                                    !passwordVisible
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
                    modifier = Modifier.height(15.dp)
                )

                // ==========================================
                // CONFIRM PASSWORD
                // ==========================================

                Text(
                    text = "Confirm Password",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorScheme.onSurface
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        errorMessage = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = {
                        Text("Confirm your password")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription =
                                "Confirm Password"
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                confirmPasswordVisible =
                                    !confirmPasswordVisible
                            }
                        ) {
                            Icon(
                                imageVector =
                                    if (confirmPasswordVisible) {
                                        Icons.Default.VisibilityOff
                                    } else {
                                        Icons.Default.Visibility
                                    },
                                contentDescription =
                                    if (confirmPasswordVisible) {
                                        "Hide password"
                                    } else {
                                        "Show password"
                                    }
                            )
                        }
                    },
                    visualTransformation =
                        if (confirmPasswordVisible) {
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

                // ==========================================
                // ERROR MESSAGE
                // ==========================================

                if (errorMessage.isNotBlank()) {

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Text(
                        text = errorMessage,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = colorScheme.error
                    )
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                // ==========================================
                // CREATE ACCOUNT BUTTON
                // ==========================================

                Button(
                    onClick = {

                        when {
                            fullName.isBlank() -> {
                                errorMessage =
                                    "Please enter your full name."
                            }

                            username.isBlank() -> {
                                errorMessage =
                                    "Please enter a username."
                            }

                            email.isBlank() -> {
                                errorMessage =
                                    "Please enter your email."
                            }

                            password.isBlank() -> {
                                errorMessage =
                                    "Please enter a password."
                            }

                            confirmPassword.isBlank() -> {
                                errorMessage =
                                    "Please confirm your password."
                            }

                            password != confirmPassword -> {
                                errorMessage =
                                    "Passwords do not match."
                            }

                            else -> {

                                val passwordHash =
                                    PasswordHasher.hash(password)

                                viewModel.registerUser(
                                    fullName = fullName.trim(),
                                    username = username.trim(),
                                    email = email.trim(),
                                    passwordHash = passwordHash
                                ) { success, message ->

                                    if (success) {

                                        navController.navigate(
                                            AppRoutes.SignIn.route
                                        ) {
                                            popUpTo(
                                                AppRoutes.SignUp.route
                                            ) {
                                                inclusive = true
                                            }
                                        }

                                    } else {

                                        errorMessage = message
                                    }
                                }
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
                        text = "Create Account",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        // ==========================================
        // SIGN IN FOOTER
        // ==========================================

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Already have an account?",
                fontSize = 14.sp,
                color = colorScheme.onSurfaceVariant
            )

            TextButton(
                onClick = {
                    navController.navigate(
                        AppRoutes.SignIn.route
                    )
                }
            ) {

                Text(
                    text = "Sign In",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.primary
                )
            }
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )
    }
}