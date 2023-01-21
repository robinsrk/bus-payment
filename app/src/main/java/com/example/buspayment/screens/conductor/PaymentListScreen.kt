package com.example.buspayment.screens.conductor

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.buspayment.navigations.Screens

@Composable
fun PaymentListScreen(navController: NavController) {
	Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
		Column {
			Box(
				Modifier
					.height(400.dp)
					.width(400.dp)
			) {
				
				Text(
					text = "Waiting for approval",
					textAlign = TextAlign.Center,
					style = MaterialTheme.typography.headlineSmall,
					modifier = Modifier
						.clickable {
							navController.navigate(Screens.UHome.route)
						}
						.fillMaxWidth()
				)
			}
		}
	}
}