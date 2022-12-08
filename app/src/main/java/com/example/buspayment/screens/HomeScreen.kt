package com.example.buspayment.screens

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.buspayment.navigations.Screens

@Composable
fun HomeScreen(navController: NavController) {
	Text(
		text = "HomeScreen",
		modifier = Modifier.clickable { navController.navigate(Screens.Scan.route) }
	)
}