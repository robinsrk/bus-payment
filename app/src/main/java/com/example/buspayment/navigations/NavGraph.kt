package com.example.buspayment.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.buspayment.LoginScreen
import com.example.buspayment.RegisterScreen
import com.example.buspayment.screens.HomeScreen
import com.example.buspayment.screens.ScanScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
	NavHost(
		navController,
		startDestination = Screens.Login.route
	) {
		composable(
			route = Screens.Login.route
		) {
			LoginScreen(navController)
		}
		composable(
			route = Screens.Register.route
		) {
			RegisterScreen(navController)
		}
		composable(
			route = Screens.Home.route
		) {
			HomeScreen(navController)
		}
		composable(
			route = Screens.Scan.route
		) {
			ScanScreen(navController)
		}
	}
}