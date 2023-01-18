package com.example.buspayment.funtions

import androidx.navigation.NavController
import com.example.buspayment.navigations.Screens
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

fun onLogin(email: String, pass: String, error: String, navController: NavController): String {
	var errorText = error
	Firebase.auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
		if (it.isSuccessful) {
			navController.navigate(Screens.UHome.route) {
				popUpTo(0)
			}
		} else {
			errorText = "Invalid email or password"
		}
	}
	return errorText
}