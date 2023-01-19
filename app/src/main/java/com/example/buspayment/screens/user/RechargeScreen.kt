package com.example.buspayment.screens.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun RechargeScreen(navController: NavController) {
	Box(Modifier.fillMaxSize()) {
		Column {
			Text(text = "Recharge")
		}
	}
}