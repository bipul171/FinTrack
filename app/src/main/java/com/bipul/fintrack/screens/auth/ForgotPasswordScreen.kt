package com.bipul.fintrack.screens.auth

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bipul.fintrack.R

@Composable
fun ForgotPasswordScreen(
    onBackClick: () -> Unit,
    onPasswordChanged: () -> Unit,
    viewModel: UserViewModel = hiltViewModel()
) {

    var username by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var newPassword by remember {
        mutableStateOf("")
    }

    var confirmPassword by remember {
        mutableStateOf("")
    }

    var showNewPassword by remember {
        mutableStateOf(false)
    }

    var showConfirmPassword by remember {
        mutableStateOf(false)
    }

    var verifiedUserId by remember {
        mutableStateOf<Long?>(null)
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
                vertical = 20.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ==========================================
        // TOP BAR
        // ==========================================

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = colorScheme.onBackground
                )
            }

            Spacer(
                modifier = Modifier.size(4.dp)
            )

            Text(
                text = "Password Recovery",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = colorScheme.onBackground
            )
        }

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        // ==========================================
        // LOGO
        // ==========================================

        Image(
            painter = painterResource(
                id = R.drawable.fintrack_logo
            ),
            contentDescription = "FinTrack Logo",
            modifier = Modifier.size(82.dp)
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        // ==========================================
        // TITLE
        // ==========================================

        Text(
            text =
                if (verifiedUserId == null) {
                    "Forgot Password?"
                } else {
                    "Create New Password"
                },
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text =
                if (verifiedUserId == null) {
                    "Verify your account to reset your password."
                } else {
                    "Your account is verified. Set a new password."
                },
            fontSize = 13.sp,
            color = colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        // ==========================================
        // FORM CARD
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
                // VERIFY ACCOUNT
                // ==========================================

                if (verifiedUserId == null) {

                    Text(
                        text = "Account Verification",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.onSurface
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text = "Enter the username and email linked to your account.",
                        fontSize = 13.sp,
                        color = colorScheme.onSurfaceVariant
                    )

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    // USERNAME

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
                            Text("Enter your username")
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Username"
                            )
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor =
                                colorScheme.primary,
                            unfocusedBorderColor =
                                colorScheme.outline,
                            focusedTextColor =
                                colorScheme.onSurface,
                            unfocusedTextColor =
                                colorScheme.onSurface,
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

                    // EMAIL

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
                            focusedBorderColor =
                                colorScheme.primary,
                            unfocusedBorderColor =
                                colorScheme.outline,
                            focusedTextColor =
                                colorScheme.onSurface,
                            unfocusedTextColor =
                                colorScheme.onSurface,
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

                    // ERROR

                    if (errorMessage.isNotBlank()) {

                        Spacer(
                            modifier = Modifier.height(12.dp)
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

                    // VERIFY BUTTON

                    Button(
                        onClick = {

                            when {

                                username.isBlank() -> {
                                    errorMessage =
                                        "Please enter your username."
                                }

                                email.isBlank() -> {
                                    errorMessage =
                                        "Please enter your email."
                                }

                                else -> {

                                    viewModel.verifyAccount(
                                        username = username.trim(),
                                        email = email.trim()
                                    ) { user ->

                                        if (user != null) {

                                            verifiedUserId =
                                                user.userId

                                            errorMessage = ""

                                        } else {

                                            errorMessage =
                                                "Username and email do not match."
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
                            containerColor =
                                colorScheme.primary,
                            contentColor =
                                colorScheme.onPrimary
                        )
                    ) {

                        Text(
                            text = "Verify Account",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                } else {

                    // ==========================================
                    // NEW PASSWORD
                    // ==========================================

                    Text(
                        text = "New Password",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.onSurface
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text = "Choose a strong password with at least 6 characters.",
                        fontSize = 13.sp,
                        color = colorScheme.onSurfaceVariant
                    )

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    // NEW PASSWORD

                    Text(
                        text = "New Password",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorScheme.onSurface
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = {
                            newPassword = it
                            errorMessage = ""
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        placeholder = {
                            Text("Enter new password")
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
                                    showNewPassword =
                                        !showNewPassword
                                }
                            ) {

                                Icon(
                                    imageVector =
                                        if (showNewPassword) {
                                            Icons.Default.VisibilityOff
                                        } else {
                                            Icons.Default.Visibility
                                        },
                                    contentDescription =
                                        if (showNewPassword) {
                                            "Hide password"
                                        } else {
                                            "Show password"
                                        }
                                )
                            }
                        },
                        visualTransformation =
                            if (showNewPassword) {
                                VisualTransformation.None
                            } else {
                                PasswordVisualTransformation()
                            },
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor =
                                colorScheme.primary,
                            unfocusedBorderColor =
                                colorScheme.outline,
                            focusedTextColor =
                                colorScheme.onSurface,
                            unfocusedTextColor =
                                colorScheme.onSurface,
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

                    // CONFIRM PASSWORD

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
                            Text("Confirm new password")
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
                                    showConfirmPassword =
                                        !showConfirmPassword
                                }
                            ) {

                                Icon(
                                    imageVector =
                                        if (showConfirmPassword) {
                                            Icons.Default.VisibilityOff
                                        } else {
                                            Icons.Default.Visibility
                                        },
                                    contentDescription =
                                        if (showConfirmPassword) {
                                            "Hide password"
                                        } else {
                                            "Show password"
                                        }
                                )
                            }
                        },
                        visualTransformation =
                            if (showConfirmPassword) {
                                VisualTransformation.None
                            } else {
                                PasswordVisualTransformation()
                            },
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor =
                                colorScheme.primary,
                            unfocusedBorderColor =
                                colorScheme.outline,
                            focusedTextColor =
                                colorScheme.onSurface,
                            unfocusedTextColor =
                                colorScheme.onSurface,
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

                    // ERROR

                    if (errorMessage.isNotBlank()) {

                        Spacer(
                            modifier = Modifier.height(12.dp)
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

                    // CHANGE PASSWORD BUTTON

                    Button(
                        onClick = {

                            when {

                                newPassword.length < 6 -> {
                                    errorMessage =
                                        "Password must be at least 6 characters."
                                }

                                newPassword != confirmPassword -> {
                                    errorMessage =
                                        "Passwords do not match."
                                }

                                else -> {

                                    val userId =
                                        verifiedUserId

                                    if (userId != null) {

                                        viewModel.changePassword(
                                            userId = userId,
                                            newPassword = newPassword
                                        ) {
                                            onPasswordChanged()
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
                            containerColor =
                                colorScheme.primary,
                            contentColor =
                                colorScheme.onPrimary
                        )
                    ) {

                        Text(
                            text = "Change Password",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        // ==========================================
        // BACK TO SIGN IN
        // ==========================================

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Remember your password?",
                fontSize = 14.sp,
                color = colorScheme.onSurfaceVariant
            )

            TextButton(
                onClick = onBackClick
            ) {

                Text(
                    text = "Sign In",
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