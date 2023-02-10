package com.example.buspayment.screens.admin

import android.util.Log
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.buspayment.navigations.Screens
import com.example.buspayment.realtimeDB.responses.RealtimeUserResponse
import com.example.buspayment.realtimeDB.ui.RealtimeViewModel

@Composable
fun ManageAccountsScreen(
	navController: NavController,
	viewModel: RealtimeViewModel = hiltViewModel()
) {
	val res = viewModel.userRes.value
	Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
		Row(
			Modifier
				.fillMaxWidth()
				.background(Color.Red, RoundedCornerShape(0.dp, 0.dp, 30.dp, 30.dp))
				.padding(20.dp),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically,
		) {
			Icon(
				modifier = Modifier.clickable {
					navController.popBackStack()
				}, imageVector = Icons.Filled.ArrowBack, contentDescription = "Back button"
			)
			Text(text = "Manage Accounts", style = MaterialTheme.typography.headlineSmall)
			Icon(modifier = Modifier.clickable {
				navController.navigate(Screens.ABus.route)
			}, imageVector = Icons.Filled.Add, contentDescription = "Add new bus")
		}
		Column {
			if (res.user.isNotEmpty()) {
				LazyColumn {
					items(
						res.user,
						key = {
							it.key!!
						}
					) { res ->
						UserRow(itemState = res.user!!)
					}
				}
			} else if (res.isLoading) {
				CircularProgressIndicator()
			}
		}
	}
}

@Composable
fun UserRow(
	itemState: RealtimeUserResponse.UserResponse
) {
	Text(itemState.email)
	Log.d("MyTag", itemState.email)
}
