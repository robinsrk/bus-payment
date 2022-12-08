@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.buspayment

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun RegisterScreen(navController: NavController) {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		Surface {
			Text(
				text = "Smart Bus Payment",
				textAlign = TextAlign.Center,
				style = MaterialTheme.typography.headlineLarge,
				color = MaterialTheme.colorScheme.secondary
			)
		}
		LogoImage()
		RegisterForm(navController)
	}
}

@Composable
fun RegisterForm(navController: NavController) {
	var email by remember { mutableStateOf("") }
	var name by remember { mutableStateOf("") }
	var num by remember { mutableStateOf("") }
	var pass by remember { mutableStateOf("") }
	var id by remember { mutableStateOf("") }
	var click by remember { mutableStateOf(false) }
	Column(horizontalAlignment = Alignment.CenterHorizontally) {
		TextField(
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
		TextField(
			value = email,
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
		TextField(
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
		TextField(
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
		TextField(
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
			Button(
				onClick = { click = !click },
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