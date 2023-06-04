package com.example.findy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.findy.composegooglemaps.compose.MapScreen
import com.example.findy.presentation.login_screen.SignInScreen
import com.example.findy.presentation.signup_screen.SignUpScreen


@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screens.MapScreen.route
    ) {
        composable(route = Screens.SignInScreen.route) {
            SignUpScreen()
        }
        composable(route = Screens.SignUpScreen.route) {
            SignInScreen()

        }
        composable(route = Screens.MapScreen.route) {
            MapScreen()
        }
    }

}