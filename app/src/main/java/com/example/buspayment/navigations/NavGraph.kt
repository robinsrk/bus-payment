package com.example.buspayment.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.buspayment.screens.admin.AdminHomeScreen
import com.example.buspayment.screens.admin.ManageAccounts
import com.example.buspayment.screens.common.LoginScreen
import com.example.buspayment.screens.common.RegisterScreen
import com.example.buspayment.screens.common.SplashScreen
import com.example.buspayment.screens.conductor.ConductorHomeScreen
import com.example.buspayment.screens.conductor.PaymentListScreen
import com.example.buspayment.screens.user.ProfileScreen
import com.example.buspayment.screens.user.ScanScreen
import com.example.buspayment.screens.user.UserHomeScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
//	val context = LocalContext.current
//	val mUserViewModel: UserViewModel =
//		viewModel(factory = UserViewModel.UserViewModelFactory(context.applicationContext as Application))
//	var email = mUserViewModel.readUser.observeAsState(listOf()).value
//	if (email.isNotEmpty()) {
//		Credentials().setEmail(email[0].email.toString())
//		navController.navigate(Screens.Home.route) {
//			popUpTo(0)
//		}
//	}
	NavHost(
		navController,
		startDestination = Screens.Splash.route
//		startDestination = if (
//			Credentials().getEmail()
//				.isNotEmpty()
//		) Screens.Home.route
//		else Screens.Login.route
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
			ManageAccounts(navController)
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
			route = Screens.Profile.route
		) {
			ProfileScreen(navController)
		}
	}
}