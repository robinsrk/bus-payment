package com.example.buspayment.navigations

sealed class Screens(val route: String) {
	// User screens
	object UHome : Screens(route = "user_home_screen")
	object Scan : Screens(route = "scan_screen")
	object Profile : Screens(route = "profile_screen")
	object Recharge : Screens(route = "Recharge_screen")
	object UHistory : Screens(route = "user_history_screen")
	object Login : Screens(route = "login_screen")
	object regUser : Screens(route = "register_user_screen")
	object allPm : Screens(route = "all_payment_screen")
	object regCon : Screens(route = "register_conductor_screen")
	object map : Screens(route = "map_screen")
	object WD : Screens(route = "withdraw_screen")
	object AHome : Screens(route = "admin_home_screen")
	object MBus : Screens(route = "mbus_screen")
	object ABus : Screens(route = "abus_screen")
	object ADist : Screens(route = "add_distance_screen")
	object MDist : Screens(route = "manage_distance_screen")
	object MQR : Screens(route = "qr_screen")
	object MAccount : Screens(route = "admin_manage_account_screen")
	object CHome : Screens(route = "conductor_home_screen")
	object PList : Screens(route = "payment_list_screen")
	object Splash : Screens(route = "splash_screen")
}
