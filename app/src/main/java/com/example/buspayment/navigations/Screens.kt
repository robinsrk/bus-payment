package com.example.buspayment.navigations

sealed class Screens(val route: String) {
	object Login : Screens(route = "login_screen")
	object Register : Screens(route = "register_screen")
	object Home : Screens(route = "home_screen")
	object Scan : Screens(route = "scan_screen")
}
