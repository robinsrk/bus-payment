package com.example.buspayment.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.buspayment.navigations.Screens

@Composable
fun HomeScreen(navController: NavController) {
	Button(onClick = { navController.navigate(Screens.Scan.route) }) {
		Text(text = "Scan")
	}
}