@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.buspayment.screens.common

import android.app.Application
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Dialpad
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.buspayment.data.User
import com.example.buspayment.data.UserViewModel
import com.example.buspayment.navigations.Screens
import com.example.buspayment.realtimeDB.responses.RealtimeUserResponse
import com.example.buspayment.realtimeDB.ui.RealtimeViewModel
import com.example.buspayment.utils.ResultState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ConductorRegistrationScreen(
	navController: NavController,
	viewModel: RealtimeViewModel = hiltViewModel()
) {
	val context = LocalContext.current
	var email by remember { mutableStateOf("") }
	var name by remember { mutableStateOf("") }
	var num by remember { mutableStateOf("") }
	var pass by remember { mutableStateOf("") }
	var cpass by remember { mutableStateOf("") }
	var error by remember { mutableStateOf("") }
	var click by remember { mutableStateOf(false) }
	val scope = rememberCoroutineScope()
	val mUserViewModel: UserViewModel =
		viewModel(factory = UserViewModel.UserViewModelFactory(context.applicationContext as Application))
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
			verticalAlignment = Alignment.CenterVertically,
		) {
			Icon(
				modifier = Modifier.clickable {
					navController.popBackStack()
				}, imageVector = Icons.Filled.ArrowBack, contentDescription = "Back button"
			)
			Text(text = "Register new account", style = MaterialTheme.typography.headlineSmall)
			Text("")
		}
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
						Icon(imageVector = Icons.Filled.Dialpad, contentDescription = "Email icon")
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
				onValueChange = { text ->
					email = text
					error = ""
				},
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
//		OutlinedTextField(
//			value = id,
//			onValueChange = { text -> id = text },
//			label = {
//				Text(text = "Student ID (optional)")
//			},
//			leadingIcon = {
//				IconButton(onClick = { /*TODO*/ }) {
//					Icon(imageVector = Icons.Filled.Email, contentDescription = "Email icon")
//				}
//			},
//			keyboardOptions = KeyboardOptions(
//				keyboardType = KeyboardType.Email,
//				imeAction = ImeAction.Next
//			),
//			modifier = Modifier.padding(
//				bottom = 12.dp,
//			)
//		)
			OutlinedTextField(
				modifier = Modifier.padding(12.dp),
				value = pass,
				onValueChange = { text ->
					pass = text
					error = ""
				},
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
					imeAction = ImeAction.Next
				),
				visualTransformation = PasswordVisualTransformation()
			)
			OutlinedTextField(
				value = cpass,
				onValueChange = { text ->
					cpass = text
					error = ""
				},
				label = {
					Text(text = "Confirm password")
				},
				leadingIcon = {
					IconButton(onClick = { }) {
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
						if (email.isNotEmpty() && name.isNotEmpty() && pass.isNotEmpty()) {
							if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
								
								if (pass.length < 6) {
									error = "Password must be at least 6 characters"
								} else {
									click = true
									Firebase.auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
										if (it.isSuccessful) {
											val userInfo =
												User(0, name, email.substringBefore("@"), email, num, "user", 0.0)
											mUserViewModel.addUser(userInfo)
											
											scope.launch(Dispatchers.Main) {
												viewModel.addUser(
													RealtimeUserResponse.UserResponse(
														userName = name,
														userId = email.substringBefore("@"),
														email,
														phone = num,
														balance = 0.0,
														role = "user"
													)
												).collect { response ->
													when (response) {
														is ResultState.Success -> {
															Toast.makeText(context, "User created", Toast.LENGTH_LONG).show()
														}
														
														is ResultState.Failure -> {
															Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show()
														}
														
														is ResultState.Loading -> {}
													}
												}
											}
											navController.navigate(Screens.UHome.route) {
												popUpTo(0)
											}
										} else {
											click = false
											error = "Unknown error"
										}
									}
									
								}
							} else {
								error = "Email is invalid"
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
					modifier = Modifier.clickable { navController.navigate(Screens.regUser.route) },
					text = "Sign up as User"
				)
			}
			Surface(modifier = Modifier.heightIn(min = 50.dp)) {
				Text(text = error, color = Color.Red)
				if (click) {
					CircularProgressIndicator()
				}
			}
			
		}
		Text("")
	}
}