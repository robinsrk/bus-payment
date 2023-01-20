package com.example.buspayment.screens.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun RechargeScreen(navController: NavController) {
	Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
		Column() {
			Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
				OutlinedCard(Modifier.height(200.dp)) {
					Text("Low package")
				}
				OutlinedCard(Modifier.height(200.dp)) {
					Text("Medium package")
				}
			}
			
			Row(horizontalArrangement = Arrangement.Center) {
				OutlinedCard {
					Text("High package")
				}
				
			}
		}
		
	}
}