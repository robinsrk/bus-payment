@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.buspayment.screens.user

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.Color
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
	user = mUserViewModel.readUser.observeAsState(emptyList()).value
	if (user.isNotEmpty()) {
		email = user[0].email
	}
	Box(
		Modifier.fillMaxSize(),
		contentAlignment = Alignment.TopCenter
	) {
		Column(
			Modifier.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.SpaceBetween
		) {
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
				Text(text = "Profile", style = MaterialTheme.typography.headlineSmall)
				Icon(
					imageVector = Icons.Default.Logout,
					contentDescription = "Forward button",
					modifier = Modifier.clickable {
						mUserViewModel.deleteUsers()
						Toast.makeText(context, "Successfully logged out", Toast.LENGTH_LONG).show()
						navController.navigate(Screens.Login.route) {
							popUpTo(0)
						}
					}
				)
			}
			Box(
				Modifier.fillMaxWidth()
			) {
				Column(
					modifier = Modifier
						.padding(20.dp)
				) {
					Row(
						Modifier
							.fillMaxWidth()
							.padding(5.dp),
						horizontalArrangement = Arrangement.SpaceBetween,
						verticalAlignment = Alignment.CenterVertically
					) {
						Text(text = "Email: ")
						OutlinedTextField(
							value = email,
							onValueChange = { text -> email = text },
						)
					}
					Row(
						Modifier
							.fillMaxWidth()
							.padding(5.dp),
						horizontalArrangement = Arrangement.SpaceBetween,
						verticalAlignment = Alignment.CenterVertically
					) {
						Text(text = "Name: ")
						OutlinedTextField(
							value = "User Name",
							onValueChange = { }
						)
					}
					Row(
						Modifier
							.fillMaxWidth()
							.padding(5.dp),
						horizontalArrangement = Arrangement.SpaceBetween,
						verticalAlignment = Alignment.CenterVertically
					) {
						Text(text = "Password: ")
						OutlinedTextField(
							value = "",
							onValueChange = { }
						)
					}
					Row(
						Modifier
							.fillMaxWidth()
							.padding(5.dp),
						horizontalArrangement = Arrangement.SpaceBetween,
						verticalAlignment = Alignment.CenterVertically
					) {
						Text(text = "Student ID: ")
						OutlinedTextField(
							value = "",
							onValueChange = { }
						)
					}
					Row(
						Modifier
							.fillMaxWidth()
							.padding(5.dp),
						horizontalArrangement = Arrangement.SpaceBetween,
						verticalAlignment = Alignment.CenterVertically
					) {
						Text(text = "Confirm Password: ")
						OutlinedTextField(
							value = "",
							onValueChange = { },
						)
					}
					Row(
						Modifier.fillMaxWidth(),
						horizontalArrangement = Arrangement.Center,
					) {
						OutlinedButton(
							onClick = {
								navController.popBackStack()
							},
							Modifier.padding(10.dp)
						) {
							Text(text = "Cancel")
						}
						OutlinedButton(
							onClick = {
								Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
							},
							Modifier.padding(10.dp)
						) {
							Text(text = "Update")
						}
						
					}
				}
			}
			Row() {
				Text(text = "")
			}
			
		}
	}
}
