@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.buspayment.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.buspayment.navigations.Screens
import com.example.buspayment.realtimeDB.responses.RealtimeDistanceResponse
import com.example.buspayment.realtimeDB.ui.RealtimeViewModel

@Composable
fun LocationsScreen(
	navController: NavController,
	viewModel: RealtimeViewModel = hiltViewModel()
) {
	val scope = rememberCoroutineScope()
	val distance = viewModel.distRes.value
	Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
		Row(
			Modifier
				.fillMaxWidth()
				.background(Color.Red, RoundedCornerShape(0.dp, 0.dp, 30.dp, 30.dp))
				.padding(20.dp),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			Icon(
				modifier = Modifier.clickable {
					navController.popBackStack()
				}, imageVector = Icons.Filled.ArrowBack, contentDescription = "Back button"
			)
			Text(text = "Manage locations", style = MaterialTheme.typography.headlineSmall)
			Icon(
				imageVector = Icons.Default.Add,
				contentDescription = "Forward button",
				modifier = Modifier.clickable {
					navController.navigate(Screens.ADist.route)
				}
			)
		}
		if (distance.dist.isNotEmpty()) {
			LazyColumn {
				items(
					distance.dist,
					key = {
						it.key!!
					}
				) { res ->
					BusList(itemState = res.dist!!)
				}
			}
		} else if (distance.isLoading) {
			CircularProgressIndicator()
		}
	}
}

@Composable
fun BusList(
	itemState: RealtimeDistanceResponse.DistanceResponse
) {
	var update by remember { mutableStateOf(false) }
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.padding(10.dp)
			.clickable {
				update = true
			}
	) {
		Row(
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier
				.fillMaxWidth()
				.padding(10.dp)
		) {
			Text(itemState.name, style = MaterialTheme.typography.bodyLarge)
			Text(text = "${itemState.value}")
		}
	}
}
