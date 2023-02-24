@file:OptIn(ExperimentalAnimationApi::class)

package com.example.buspayment.navigations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.buspayment.screens.admin.AddBusScreen
import com.example.buspayment.screens.admin.AddLocationByMap
import com.example.buspayment.screens.admin.AddLocationScreen
import com.example.buspayment.screens.admin.AdminHomeScreen
import com.example.buspayment.screens.admin.LocationsScreen
import com.example.buspayment.screens.admin.ManageAccountsScreen
import com.example.buspayment.screens.admin.ManageBusScreen
import com.example.buspayment.screens.common.ConductorRegistrationScreen
import com.example.buspayment.screens.common.LoginScreen
import com.example.buspayment.screens.common.SplashScreen
import com.example.buspayment.screens.common.UserRegistrationScreen
import com.example.buspayment.screens.conductor.AllPaymentListScreen
import com.example.buspayment.screens.conductor.ConductorHomeScreen
import com.example.buspayment.screens.conductor.ConductorQRCode
import com.example.buspayment.screens.conductor.PaymentListScreen
import com.example.buspayment.screens.user.ProfileScreen
import com.example.buspayment.screens.user.RechargeScreen
import com.example.buspayment.screens.user.ScanScreen
import com.example.buspayment.screens.user.UserHistoryScreen
import com.example.buspayment.screens.user.UserHomeScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@Composable
fun SetupNavGraph(navController: NavHostController) {
	AnimatedNavHost(
		navController,
		startDestination = Screens.Splash.route
	) {
		composable(
			route = Screens.Login.route
		) {
			LoginScreen(navController)
		}
		composable(
			route = Screens.regUser.route
		) {
			UserRegistrationScreen(navController)
		}
		
		composable(
			route = Screens.allPm.route
		) {
			AllPaymentListScreen(navController)
		}
		composable(
				route = Screens.regCon.route
				) {
			ConductorRegistrationScreen(navController)
		}
		composable(
			route = Screens.UHome.route
		) {
			UserHomeScreen(navController)
		}
		composable(
			route = Screens.AHome.route
		) {
			AdminHomeScreen(navController)
		}
		composable(
			route = Screens.CHome.route
		) {
			ConductorHomeScreen(navController)
		}
		composable(
			route = Screens.MAccount.route
		) {
			ManageAccountsScreen(navController)
		}
		
		composable(
			route = Screens.PList.route
		) {
			PaymentListScreen(navController)
		}
		composable(
			route = Screens.Scan.route
		) {
			ScanScreen(navController)
		}
		composable(
			route = Screens.Splash.route
		) {
			SplashScreen(navController)
		}
		composable(
			route = Screens.UHistory.route
		) {
			UserHistoryScreen(navController)
		}
		composable(
			route = Screens.Recharge.route
		) {
			RechargeScreen(navController)
		}
		composable(
			route = Screens.Profile.route
		) {
			ProfileScreen(navController)
		}
		composable(
			route = Screens.MBus.route
		) {
			ManageBusScreen(navController)
		}
		composable(
			route = Screens.ABus.route
		) {
			AddBusScreen(navController)
		}
		composable(
			route = Screens.MQR.route
		) {
			ConductorQRCode(navController)
		}
		composable(
			route = Screens.MDist.route
		) {
			LocationsScreen(navController)
		}
		composable(
			route = Screens.ADist.route
		) {
			AddLocationScreen(navController)
		}
		composable(
			route = Screens.map.route
		) {
			AddLocationByMap(navController)
		}
	}
}