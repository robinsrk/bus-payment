package com.example.buspayment.screens.conductor

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.buspayment.navigations.Screens

@Composable
fun PaymentListScreen(navController: NavController) {
	Box {
		Column {
			Text(modifier = Modifier.clickable {
				navController.navigate(
					Screens.CHome.route
				)
			}, text = "Hello world")
		}
	}
}