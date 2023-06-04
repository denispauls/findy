package com.example.findy.navigation

sealed class Screens(val route: String) {
    object SignInScreen : Screens(route = "SignIn_Screen")
    object SignUpScreen : Screens(route = "SignUp_Screen")
    object MapScreen : Screens(route = "Maps_Screen")
}