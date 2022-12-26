@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.buspayment.screens

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.buspayment.data.User
import com.example.buspayment.data.UserDatabase
import com.example.buspayment.data.UserViewModel
import com.example.buspayment.funtions.Credentials
import com.example.buspayment.navigations.Screens
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun LoginScreen(navController: NavController) {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
		modifier = Modifier.fillMaxSize()
	) {
		Text(
			text = "Login",
			textAlign = TextAlign.Center,
			style = MaterialTheme.typography.headlineLarge,
			color = MaterialTheme.colorScheme.secondary
		)
		LoginForm(navController)
	}
}

@Composable
fun LoginForm(navController: NavController) {
	var email by remember { mutableStateOf("") }
	var error by remember { mutableStateOf("") }
	var pass by remember { mutableStateOf("") }
	var isLoading by remember { mutableStateOf(false) }
	val context = LocalContext.current
	val mUserViewModel: UserViewModel = viewModel(
		factory = UserViewModel.UserViewModelFactory(context.applicationContext as Application)
	)
	val db = UserDatabase.getDatabase(context)
	Column(horizontalAlignment = Alignment.CenterHorizontally) {
		OutlinedTextField(
			value = email,
			onValueChange = { text -> email = text },
			label = {
				Text(text = "Email address")
			},
			leadingIcon = {
				IconButton(onClick = { /*TODO*/ }) {
					Icon(imageVector = Icons.Filled.Email, contentDescription = "Email icon")
				}
			},
			keyboardOptions = KeyboardOptions(
				keyboardType = KeyboardType.Email,
				imeAction = ImeAction.Next
			),
			modifier = Modifier.padding(
				12.dp,
			)
		)
		OutlinedTextField(
			value = pass,
			onValueChange = { text -> pass = text },
			label = {
				Text(text = "Password")
			},
			leadingIcon = {
				IconButton(onClick = { /*TODO*/ }) {
					Icon(imageVector = Icons.Filled.Lock, contentDescription = "Lock icon")
				}
			},
			keyboardOptions = KeyboardOptions(
				keyboardType = KeyboardType.Password,
				imeAction = ImeAction.Done
			),
			visualTransformation = PasswordVisualTransformation()
		)
		Row(
			horizontalArrangement = Arrangement.Center,
			verticalAlignment = Alignment.CenterVertically,
		) {
			OutlinedButton(
				onClick = {
					error = ""
					if (email.isNotEmpty() && pass.isNotEmpty()) {
						isLoading = true
						Firebase.auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
							if (it.isSuccessful) {
								isLoading = false
								navController.navigate(Screens.Home.route) {
									popUpTo(0)
								}
								val user = User(0, "", email)
								mUserViewModel.deleteUsers()
								mUserViewModel.addUser(user)
								Credentials().setEmail(email)
								Toast.makeText(context, "Successfully logged in", Toast.LENGTH_LONG).show()
							} else {
								error = "Wrong username or password"
								isLoading = false
							}
						}
					} else {
						error = "Fill all the fields"
					}
				},
				modifier = Modifier.padding(10.dp)
			) {
				Text("Login")
			}
			Text(
				modifier = Modifier.clickable { navController.navigate(Screens.Register.route) },
				text = "Create a new account"
			)
		}
		Surface(
			modifier = Modifier
				.heightIn(min = 20.dp),
		) {
			Text(error, color = Color.Red)
		}
		Surface(modifier = Modifier.heightIn(min = 50.dp)) {
			if (isLoading) {
				CircularProgressIndicator()
			}
		}

	}
}