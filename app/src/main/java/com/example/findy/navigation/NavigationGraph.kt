package com.example.findy.navigation

import androidx.activity.compose.LocalActivityResultRegistryOwner.current
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.findy.MainActivity
//import com.example.findy.MapActivity
import com.example.findy.presentation.login_screen.SignInScreen
//import com.example.findy.presentation.maps.MapScreen
import com.example.findy.presentation.signup_screen.SignUpScreen
import com.example.findy.ui.theme.FindyTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import androidx.compose.ui.platform.LocalContext

/*
@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController()
) {

    NavHost(
        navController = navController,
        startDestination = Screens.MapScreen.route
    ) {
        composable(route = Screens.SignInScreen.route) {
            SignInScreen(navController = navController)
        }
        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen(navController = navController)

        }
        composable(route = Screens.MapScreen.route) {
           //MapScreen()
        }
    }
}
*/



