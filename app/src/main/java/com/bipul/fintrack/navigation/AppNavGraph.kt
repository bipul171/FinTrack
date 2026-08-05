package com.bipul.fintrack.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bipul.fintrack.screens.auth.SignInScreen
import com.bipul.fintrack.screens.auth.SignUpScreen
import com.bipul.fintrack.screens.budget.BudgetScreen
import com.bipul.fintrack.screens.home.HomeScreen
import com.bipul.fintrack.screens.transaction.TransactionScreen
import com.bipul.fintrack.screens.welcome.WelcomeScreenOne
import com.bipul.fintrack.screens.welcome.WelcomeScreenTwo
import com.bipul.fintrack.screens.welcome.WelcomeScreenThree

@Composable
fun WelcomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.WelcomeOne.route
    ) {
        composable(AppRoutes.WelcomeOne.route) {
            WelcomeScreenOne(navController)
        }

        composable(AppRoutes.WelcomeTwo.route) {
            WelcomeScreenTwo(navController)
        }

        composable(AppRoutes.WelcomeThree.route) {
            WelcomeScreenThree(navController)
        }

        composable(AppRoutes.SignUp.route) {
            SignUpScreen(navController)
        }

        composable(AppRoutes.SignIn.route) {
            SignInScreen(navController)
        }

        composable(AppRoutes.Home.route) {
            HomeScreen(navController)
        }

        composable(AppRoutes.Transaction.route) {
            TransactionScreen(navController)
        }

        composable(AppRoutes.Budget.route){
            BudgetScreen( navController)
        }
    }
}
