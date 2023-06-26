package com.example.findy

import MapScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
//import com.example.findy.navigation.NavigationGraph
import com.example.findy.navigation.Screens
import com.example.findy.presentation.login_screen.SignInScreen
import com.example.findy.presentation.signup_screen.SignUpScreen
import com.example.findy.ui.theme.FindyTheme
import com.example.findy.userlist.FriendsScreen
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            MainScreen(fusedLocationProviderClient)
        }
    }
}

@Composable
fun MainScreen(fusedLocationProviderClient: FusedLocationProviderClient) {
    FindyTheme() {
        NavigationGraph(fusedLocationProviderClient)
    }
}

@Composable
fun NavigationGraph(fusedLocationProviderClient: FusedLocationProviderClient) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.SignInScreen.route
    ) {
        composable(route = Screens.SignInScreen.route) {
            SignInScreen(navController = navController)
        }
        composable(route = Screens.SignUpScreen.route) {
            SignUpScreen(navController = navController)
        }
        composable(route = Screens.MapScreen.route) {
            MapScreen(fusedLocationProviderClient, navController = navController)
        }
        composable(route = Screens.FriendsScreen.route) {
            FriendsScreen(navController = navController)
        }
    }
}
