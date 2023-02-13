@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.buspayment.screens.common

import android.app.Application
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
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
import com.example.buspayment.realtimeDB.ui.RealtimeViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun LoginScreen(
	navController: NavController,
	viewModel: RealtimeViewModel = hiltViewModel()
) {
	val userList = viewModel.userRes.value
	val scope = rememberCoroutineScope()
	Log.d("userList", userList.toString())
	var email by remember { mutableStateOf("") }
	var error by remember { mutableStateOf("") }
	var pass by remember { mutableStateOf("") }
	var isLoading by remember { mutableStateOf(false) }
	var selectedRole by remember { mutableStateOf("") }
	val context = LocalContext.current
	val mUserViewModel: UserViewModel = viewModel(
		factory = UserViewModel.UserViewModelFactory(context.applicationContext as Application)
	)
	LaunchedEffect(key1 = true) {
		viewModel.getUser()
	}
	
	Column(
		Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.SpaceBetween,
	) {
		Row(
			Modifier
				.fillMaxWidth()
				.background(Color.Red, RoundedCornerShape(0.dp, 0.dp, 30.dp, 30.dp))
				.padding(20.dp),
			horizontalArrangement = Arrangement.Center,
			verticalAlignment = Alignment.CenterVertically,
		) {
			Text(text = "Login", style = MaterialTheme.typography.headlineSmall)
		}
		Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
					12.dp,
				)
			)
			OutlinedTextField(
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
					imeAction = ImeAction.Done
				),
				visualTransformation = PasswordVisualTransformation()
			)

//			Row() {
//				OutlinedTextField(
//					value = selectedRole,
//					readOnly = false,
//					onValueChange = { selectedRole = it },
//					modifier = Modifier
//						.onGloballyPositioned { coordinates ->
//							//This value is used to assign to the DropDown the same width
//							textFieldSize = coordinates.size.toSize()
//						}
//						.padding(12.dp)
//						.clickable { isExpanded = !isExpanded },
//
//					label = { Text("Role") },
//					trailingIcon = {
//						Icon(icon, "contentDescription",
//							Modifier.clickable { isExpanded = !isExpanded })
//					},
//					leadingIcon = {
//						IconButton(onClick = { /*TODO*/ }) {
//							Icon(imageVector = Icons.Filled.Person2, contentDescription = "Role icon")
//						}
//					},
//				)
//
//			}
//			DropdownMenu(
//				expanded = isExpanded,
//				onDismissRequest = { isExpanded = false },
//				modifier = Modifier
//					.width(with(LocalDensity.current) { textFieldSize.width.toDp() })
//			) {
//				listItems.forEach { label ->
//					DropdownMenuItem(
//						onClick = {
//							selectedRole = label
//							isExpanded = !isExpanded
//						},
//						text = { Text(text = label) }
//					)
//				}
//			}
			Row(
				horizontalArrangement = Arrangement.Center,
				verticalAlignment = Alignment.CenterVertically,
			) {
				OutlinedButton(
					onClick = {
						error = ""
						if (email.isNotEmpty() && pass.isNotEmpty()) {
							if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
								if (pass.length > 5) {
									isLoading = true
									Firebase.auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
										if (it.isSuccessful) {
											scope.launch {
												withContext(Dispatchers.IO) {
													userList.user.forEach { item ->
														if (email == item.user!!.email) {
															selectedRole = item.user.role
															val user = User(
																0,
																item.user.userName,
																item.user.userId,
																email,
																item.user.phone,
																selectedRole,
																item.user.balance
															)
															mUserViewModel.addUser(user)
															
														}
													}
												}
												if (selectedRole == "user") {
													navController.navigate(Screens.UHome.route) {
														popUpTo(0)
													}
												} else if (selectedRole == "conductor") {
													navController.navigate(Screens.CHome.route) {
														popUpTo(0)
													}
												}
												if (selectedRole == "admin") {
													navController.navigate(Screens.AHome.route) {
														popUpTo(0)
													}
												}
											}
											isLoading = false
											Toast.makeText(
												context,
												"Welcome ${email.substringBefore("@")}",
												Toast.LENGTH_LONG
											).show()
										} else {
											error = "Wrong username or password"
											isLoading = false
										}
									}
								} else {
									error = "Password must be at least 6 characters long"
								}
							} else {
								error = "Email is invalid"
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
			
		}
		Column {
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
}