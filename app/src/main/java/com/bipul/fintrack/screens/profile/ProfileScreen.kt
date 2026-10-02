package com.bipul.fintrack.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bipul.fintrack.data.local.session.SessionManager
import com.bipul.fintrack.navigation.AppRoutes

@Composable
fun ProfileScreen(
    navController: NavHostController,
    sessionManager: SessionManager
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Profile",
            modifier = Modifier
                .height(80.dp)
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "Md. Bipul Mia",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "bipul@example.com",
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        ProfileOption(
            icon = Icons.Default.Edit,
            title = "Edit Profile",
            onClick = {
                // Later
            }
        )

        ProfileOption(
            icon = Icons.Default.Lock,
            title = "Change Password",
            onClick = {
                // Later
            }
        )

        ProfileOption(
            icon = Icons.Default.Fingerprint,
            title = "Biometric Authentication",
            onClick = {
                // Later
            }
        )

        ProfileOption(
            icon = Icons.Default.Settings,
            title = "Settings",
            onClick = {
                // Later
            }
        )

        ProfileOption(
            icon = Icons.Default.Logout,
            title = "Logout",
            onClick = {
                sessionManager.clearSession()

                navController.navigate(AppRoutes.SignIn.route) {
                    popUpTo(AppRoutes.Home.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

@Composable
fun ProfileOption(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Icon(
                imageVector = icon,
                contentDescription = title
            )

            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}