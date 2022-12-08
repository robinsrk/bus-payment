@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.buspayment

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.buspayment.navigations.Screens

@Composable
fun LoginScreen(navController: NavController) {
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
		LoginForm(navController)
	}
}

@Composable
fun LoginForm(navController: NavController) {
	var email by remember { mutableStateOf("") }
	var pass by remember { mutableStateOf("") }
	var click by remember { mutableStateOf(false) }
	Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
				12.dp,
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
				onClick = { navController.navigate(Screens.Home.route) },
//				onClick = { click = !click },
				modifier = Modifier.padding(10.dp)
			) {
				Text("Login")
			}
			Text(
				modifier = Modifier.clickable { navController.navigate(Screens.Register.route) },
				text = "Create a new account"
			)
		}
		Surface(modifier = Modifier.heightIn(min = 50.dp)) {
			if (click) {
				CircularProgressIndicator()
			}
		}

	}
}

@Composable
fun LogoImage() {
	Box(
		modifier = Modifier
			.height(200.dp)
			.width(300.dp)
			.clip(RoundedCornerShape(20.dp))
	) {
		val painter =
			rememberImagePainter(
				data = R.drawable.undraw_bus_stop_re_h8ej,
				builder = {}
			)
		Image(painter, contentDescription = "Logo")
	}
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun preview() {
	LoginScreen(navController = rememberNavController())
}