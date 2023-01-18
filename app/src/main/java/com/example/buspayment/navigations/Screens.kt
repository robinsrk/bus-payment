package com.example.buspayment.navigations

sealed class Screens(val route: String) {
	object Login : Screens(route = "login_screen")
	object Register : Screens(route = "register_screen")
	object UHome : Screens(route = "user_home_screen")
	object AHome : Screens(route = "admin_home_screen")
	object MAccount : Screens(route = "admin_manage_account_screen")
	object CHome : Screens(route = "conductor_home_screen")
	object PList : Screens(route = "payment_list_screen")
	object Scan : Screens(route = "scan_screen")
	object Splash : Screens(route = "splash_screen")
	object Profile : Screens(route = "profile_screen")
}
