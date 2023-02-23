
@file:OptIn(
	ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
	ExperimentalMaterial3Api::class
)

package com.example.buspayment.screens.admin

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.buspayment.realtimeDB.responses.RealtimeBusResponse
import com.example.buspayment.realtimeDB.ui.RealtimeViewModel
import com.example.buspayment.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AddBusScreen(
	navController: NavController,
	viewModel: RealtimeViewModel = hiltViewModel()
) {
	var busName by remember { mutableStateOf("") }
	var email by remember { mutableStateOf("") }
	var userName by remember { mutableStateOf("") }
	var id by remember { mutableStateOf("") }
	val scope = rememberCoroutineScope()
	val context = LocalContext.current
	Column {
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
			Text(text = "Manage buses", style = MaterialTheme.typography.headlineSmall)
			Text(text = "", style = MaterialTheme.typography.headlineSmall)
		}
		Column(
			Modifier.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
			OutlinedTextField(
				email,
				{ text ->
					email = text
				},
				label = {
					Text(text = "Email address")
				}
			)
			OutlinedTextField(
				userName,
				{ text ->
					userName = text
				},
				label = {
					Text(text = "User name")
				}
			)
			OutlinedTextField(
				id,
				{ text ->
					id = text
				},
				label = {
					Text(text = "Bus ID")
				}
			)
			OutlinedTextField(
				busName,
				{ text ->
					busName = text
				},
				label = {
					Text(text = "Bus name")
				}
			)
			Button(onClick = {
				scope.launch(Dispatchers.Main) {
					viewModel.addBus(
						RealtimeBusResponse.BusResponse(
							name = busName,
							id,
							"Chandra",
							"Mirpur"
						)
					).collect { response ->
						when (response) {
							is ResultState.Success -> {
								Toast.makeText(context, "New bus account created", Toast.LENGTH_LONG).show()
							}
							
							is ResultState.Failure -> {
								Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show()
							}
							
							is ResultState.Loading -> {}
						}
					}
				}
			}) {
				Text("Submit")
			}
		}
	}
}