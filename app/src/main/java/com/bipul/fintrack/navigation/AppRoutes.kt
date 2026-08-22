package com.bipul.fintrack.navigation

sealed class AppRoutes(val route: String) {

    object SplashScreen : AppRoutes("splash_screen")
    object WelcomeOne : AppRoutes("welcome_one")
    object WelcomeTwo : AppRoutes("welcome_two")
    object WelcomeThree : AppRoutes("welcome_three")

    object SignUp : AppRoutes("sign_up")

    object SignIn : AppRoutes("sign_in")

    object Home : AppRoutes("home")

    object Transaction : AppRoutes(route = "Transaction")

    object Budget : AppRoutes(route = "Budget")

    object AddBudget : AppRoutes(route = "add_budget")

    data object ForgotPassword : AppRoutes(route = "forgot_password")
}