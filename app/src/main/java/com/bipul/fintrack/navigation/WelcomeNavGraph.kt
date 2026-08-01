package com.bipul.fintrack.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bipul.fintrack.screens.auth.SignInScreen
import com.bipul.fintrack.screens.auth.SignUpScreen
import com.bipul.fintrack.screens.home.HomeScreen
import com.bipul.fintrack.screens.welcome.WelcomeScreenOne
import com.bipul.fintrack.screens.welcome.WelcomeScreenTwo
import com.bipul.fintrack.screens.welcome.WelcomeScreenThree

@Composable
fun WelcomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = WelcomeNavRoutes.WelcomeOne.route
    ) {
        composable(WelcomeNavRoutes.WelcomeOne.route) {
            WelcomeScreenOne(navController)
        }

        composable(WelcomeNavRoutes.WelcomeTwo.route) {
            WelcomeScreenTwo(navController)
        }

        composable(WelcomeNavRoutes.WelcomeThree.route) {
            WelcomeScreenThree(navController)
        }

        composable(WelcomeNavRoutes.SignUp.route) {
            SignUpScreen(navController)
        }

        composable(WelcomeNavRoutes.SignIn.route) {
            SignInScreen(navController)
        }

        composable(WelcomeNavRoutes.Home.route) {
            HomeScreen(navController)
        }
    }
}
