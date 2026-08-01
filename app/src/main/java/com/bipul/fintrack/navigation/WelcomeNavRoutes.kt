package com.bipul.fintrack.navigation

sealed class WelcomeNavRoutes(val route: String) {
    object WelcomeOne : WelcomeNavRoutes("welcome_one")
    object WelcomeTwo : WelcomeNavRoutes("welcome_two")
    object WelcomeThree : WelcomeNavRoutes("welcome_three")

    object SignUp : WelcomeNavRoutes("sign_up")

    object SignIn : WelcomeNavRoutes("sign_in")

    object Home : WelcomeNavRoutes("home")
}
