@file:OptIn(ExperimentalMaterial3Api::class)

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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.buspayment.navigations.Screens
import com.example.buspayment.realtimeDB.responses.RealtimeDistanceResponse
import com.example.buspayment.realtimeDB.ui.RealtimeViewModel
import com.example.buspayment.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AddLocationScreen(
	navController: NavController,
	viewModel: RealtimeViewModel = hiltViewModel()
) {
	var name by remember { mutableStateOf("") }
	val scope = rememberCoroutineScope()
	val context = LocalContext.current
	var value by remember { mutableStateOf("") }
	var lat1 by remember { mutableStateOf("") }
	var lat2 by remember { mutableStateOf("") }
	var lat3 by remember { mutableStateOf("") }
	var lat4 by remember { mutableStateOf("") }
	var lng1 by remember { mutableStateOf("") }
	var lng2 by remember { mutableStateOf("") }
	var lng3 by remember { mutableStateOf("") }
	var lng4 by remember { mutableStateOf("") }
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
			Text(text = "Add location", style = MaterialTheme.typography.headlineSmall)
			Icon(
				modifier = Modifier.clickable {
					navController.navigate(Screens.map.route)
				}, imageVector = Icons.Filled.ArrowBack, contentDescription = "Back button"
			)
		}
		Column(
			Modifier.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
			OutlinedTextField(
				name,
				{ text ->
					name = text
				},
				label = {
					Text(text = "Location name")
				},
				keyboardOptions = KeyboardOptions(
					keyboardType = KeyboardType.Email,
					imeAction = ImeAction.Next
				),
			)
			OutlinedTextField(
				value = value,
				{ text ->
					value = text
				},
				label = {
					Text(text = "Value")
				},
				keyboardOptions = KeyboardOptions(
					keyboardType = KeyboardType.Email,
					imeAction = ImeAction.Next
				),
			)
			OutlinedTextField(
				value = lat1,
				{ text: String ->
					lat1 = text
				},
				label = {
					Text(text = "Latitude 1")
				},
				keyboardOptions = KeyboardOptions(
					keyboardType = KeyboardType.Email,
					imeAction = ImeAction.Next
				),
			)
			OutlinedTextField(
				lng1,
				{ text ->
					lng1 = text
				},
				label = {
					Text(text = "Longitude 1")
				},
				keyboardOptions = KeyboardOptions(
					keyboardType = KeyboardType.Email,
					imeAction = ImeAction.Next
				),
			)
			OutlinedTextField(
				lat2,
				{ text ->
					lat2 = text
				},
				label = {
					Text(text = "Latitude 2")
				},
				keyboardOptions = KeyboardOptions(
					keyboardType = KeyboardType.Email,
					imeAction = ImeAction.Next
				),
			)
			OutlinedTextField(
				lng2,
				{ text ->
					lng2 = text
				},
				label = {
					Text(text = "Longitude 2")
				},
				keyboardOptions = KeyboardOptions(
					keyboardType = KeyboardType.Email,
					imeAction = ImeAction.Next
				),
			)
			OutlinedTextField(
				lat3,
				{ text ->
					lat3 = text
				},
				label = {
					Text(text = "Latitude 3")
				},
				keyboardOptions = KeyboardOptions(
					keyboardType = KeyboardType.Email,
					imeAction = ImeAction.Next
				),
			)
			OutlinedTextField(
				lng3,
				{ text ->
					lng3 = text
				},
				label = {
					Text(text = "Longitude 3")
				},
				keyboardOptions = KeyboardOptions(
					keyboardType = KeyboardType.Email,
					imeAction = ImeAction.Next
				),
			)
			OutlinedTextField(
				lat4,
				{ text ->
					lat4 = text
				},
				label = {
					Text(text = "Latitude 4")
				},
				keyboardOptions = KeyboardOptions(
					keyboardType = KeyboardType.Email,
					imeAction = ImeAction.Next
				),
			)
			OutlinedTextField(
				lng4,
				{ text ->
					lng4 = text
				},
				label = {
					Text(text = "Longitude 4")
				},
				keyboardOptions = KeyboardOptions(
					keyboardType = KeyboardType.Email,
					imeAction = ImeAction.Next
				),
			)
			Button(onClick = {
				scope.launch(Dispatchers.Main) {
					viewModel.addDistance(
						RealtimeDistanceResponse.DistanceResponse(
							name = name,
							value = value.toDouble(),
							lat1 = lat1.toDouble(),
							lat2 = lat2.toDouble(),
							lat3 = lat3.toDouble(),
							lat4 = lat4.toDouble(),
							lng1 = lng1.toDouble(),
							lng2 = lng2.toDouble(),
							lng3 = lng3.toDouble(),
							lng4 = lng4.toDouble(),
						)
					).collect { response ->
						when (response) {
							is ResultState.Success -> {
								Toast.makeText(context, "Data added", Toast.LENGTH_LONG).show()
								navController.popBackStack()
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