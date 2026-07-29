package com.bipul.fintrack.screens.welcome

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bipul.fintrack.R
import com.bipul.fintrack.navigation.WelcomeNavRoutes
import com.bipul.fintrack.ui.theme.FinTrackPrimary


@Composable
fun WelcomeScreenThree(navController: NavHostController) {


    Scaffold(
        containerColor = Color.Black,
    ) { innerPadding ->


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 16.dp)

        ) {


            LinearProgressIndicator(
                color = Color(0xFF5865F2),
                trackColor = Color(0xFF2C2C2C),
                progress = { 1f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
            )

            Spacer(Modifier.size(size = 24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                IconButton(
                    onClick = {
                        if (navController.previousBackStackEntry != null) {
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier

                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }


            Spacer(Modifier.size(size = 40.dp))

            Image(

                painter = painterResource(id = R.drawable.onboarding_wallet_3),
                contentDescription = "Wallet",
                modifier = Modifier
                    .width(400.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.weight(0.7f))

            Text(
                text = "Adding Payments Made Effortless",
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 40.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.size(size = 16.dp))

            Text(
                text = "Effortlessly manage your income and expenses for a stress-free financial future",
                color = Color(0xFF9E9E9E),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    navController.navigate(WelcomeNavRoutes.SignUp.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 0.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = FinTrackPrimary,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Join One",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.size(size = 24.dp))

            Button(
                onClick = {
                    navController.navigate(WelcomeNavRoutes.SignIn.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 0.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color.Black,
                    disabledContentColor = Color.Gray,
                    disabledContainerColor = Color.Gray
                )
            ) {
                Text(
                    text = "Sign in",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = FinTrackPrimary
                )
            }

            Spacer(Modifier.size(size = 24.dp))


        }


    }


}
