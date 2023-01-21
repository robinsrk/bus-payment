package com.example.buspayment.screens.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun RechargeScreen(navController: NavController) {
	Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
		Column() {
			Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
				Card(
					Modifier
						.height(200.dp)
						.width(200.dp)
				) {
					Text(
						"50 taka",
						Modifier.fillMaxWidth(),
						textAlign = TextAlign.Center,
						style = MaterialTheme.typography.headlineSmall
					)
				}
				Card(
					Modifier
						.height(200.dp)
						.width(200.dp)
				) {
					Text(
						"100 taka",
						Modifier.fillMaxWidth(),
						textAlign = TextAlign.Center,
						style = MaterialTheme.typography.headlineSmall
					)
				}
			}
			
			Row(
				Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.Center,
			) {
				Card(
					Modifier
						.height(200.dp)
						.width(200.dp)
				) {
					Text(
						"500 taka",
						Modifier.fillMaxWidth(),
						textAlign = TextAlign.Center,
						style = MaterialTheme.typography.headlineSmall
					)
				}
				
			}
		}
		
	}
}