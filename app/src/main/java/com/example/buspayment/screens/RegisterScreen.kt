@file:OptIn(ExperimentalMaterial3Api::class)

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.buspayment.data.User
import com.example.buspayment.data.UserViewModel
import com.example.buspayment.navigations.Screens
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun RegisterScreen(navController: NavController) {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
		modifier = Modifier.fillMaxSize()
	) {
		Surface {
			Text(
				text = "Register new account",
				textAlign = TextAlign.Center,
				style = MaterialTheme.typography.headlineLarge,
				color = MaterialTheme.colorScheme.secondary
			)
		}
		RegisterForm(navController)
	}
}

@Composable
fun RegisterForm(navController: NavController) {
	val context = LocalContext.current
	var email by remember { mutableStateOf("") }
	var name by remember { mutableStateOf("") }
	var num by remember { mutableStateOf("") }
	var pass by remember { mutableStateOf("") }
	var id by remember { mutableStateOf("") }
	var error by remember { mutableStateOf("") }
	var click by remember { mutableStateOf(false) }
	val mUserViewModel: UserViewModel =
		viewModel(factory = UserViewModel.UserViewModelFactory(context.applicationContext as Application))
	Column(horizontalAlignment = Alignment.CenterHorizontally) {
		OutlinedTextField(
			value = name,
			onValueChange = { text -> name = text },
			label = {
				Text(text = "Full name")
			},
			leadingIcon = {
				IconButton(onClick = { /*TODO*/ }) {
					Icon(imageVector = Icons.Filled.Email, contentDescription = "Email icon")
				}
			},
			keyboardOptions = KeyboardOptions(
				keyboardType = KeyboardType.Text,
				imeAction = ImeAction.Next
			),
			modifier = Modifier.padding(
				bottom = 12.dp
			)
		)
		OutlinedTextField(
			value = num,
			onValueChange = { text -> num = text },
			label = {
				Text(text = "Phone number")
			},
			leadingIcon = {
				IconButton(onClick = { /*TODO*/ }) {
					Icon(imageVector = Icons.Filled.Email, contentDescription = "Email icon")
				}
			},
			keyboardOptions = KeyboardOptions(
				keyboardType = KeyboardType.Number,
				imeAction = ImeAction.Next
			),
			modifier = Modifier.padding(
				bottom = 12.dp,
			)
		)
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
				bottom = 12.dp,
			)
		)
		OutlinedTextField(
			value = id,
			onValueChange = { text -> id = text },
			label = {
				Text(text = "Student ID (optional)")
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
				bottom = 12.dp,
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
					if (email.isNotEmpty() && name.isNotEmpty() && pass.isNotEmpty()) {
						click = true
						Firebase.auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
							if (it.isSuccessful) {
								val userInfo = User(0, name, email)
								mUserViewModel.addUser(userInfo)
								navController.navigate(Screens.Home.route) {
									popUpTo(0)
								}
								Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
							} else {
								click = false
								Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
							}
						}
					} else {
						error = "Fill all the required fields"
					}
				},
				modifier = Modifier.padding(10.dp)
			) {
				Text(text = if (click) "Cancel" else "Register")
			}
			Text(
				modifier = Modifier.clickable { navController.popBackStack() },
				text = "Login with existing account"
			)
		}
		Surface(modifier = Modifier.heightIn(min = 50.dp)) {
			Text(text = error, color = Color.Red)
			if (click) {
				CircularProgressIndicator()
			}
		}
	}
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewRegister() {
	Surface {
		RegisterScreen(navController = rememberNavController())
	}
}