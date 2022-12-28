@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.buspayment.screens

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.buspayment.data.User
import com.example.buspayment.data.UserViewModel
import com.example.buspayment.navigations.Screens

@Composable
fun ProfileScreen(navController: NavController) {
	val context = LocalContext.current
	var email by remember { mutableStateOf("") }
	var user by remember { mutableStateOf(listOf<User>()) }
	val mUserViewModel: UserViewModel =
		viewModel(factory = UserViewModel.UserViewModelFactory(context.applicationContext as Application))
	user = mUserViewModel.readUser.observeAsState(listOf()).value
	if (user.isNotEmpty()) {
		email = user[0].email
	}
	Box(
		Modifier.fillMaxSize(),
		contentAlignment = Alignment.Center
	) {
		Column(
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text(text = "Profile", style = MaterialTheme.typography.headlineLarge)
			OutlinedCard(
				Modifier
					.heightIn(max = 500.dp)
					.widthIn(max = 300.dp)
			) {
				Column(
					modifier = Modifier
						.fillMaxSize()
						.padding(20.dp)
				) {
					Row(
						verticalAlignment = Alignment.CenterVertically
					) {
						Text(text = "Email: ")
						OutlinedTextField(value = email, onValueChange = { text -> email = text })
					}
				}
			}
			Row() {
				OutlinedButton(
					onClick = {
						navController.navigate(Screens.Home.route) {
							popUpTo(0)
						}
					},
					Modifier.padding(20.dp)
				) {
					Text(text = "Cancel")
				}
				OutlinedButton(
					onClick = {
						Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
					},
					Modifier.padding(20.dp)
				) {
					Text(text = "Update")
				}
				
			}
			
		}
	}
}
