@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.buspayment.screens.conductor

import android.app.Application
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.buspayment.data.User
import com.example.buspayment.data.UserViewModel
import com.example.buspayment.navigations.Screens
import com.example.buspayment.realtimeDB.ui.RealtimeViewModel
import com.example.buspayment.ui.theme.LogoImage

@Composable
fun ConductorHomeScreen(
	navController: NavController,
	viewModel: RealtimeViewModel = hiltViewModel()
) {
	val context = LocalContext.current
	val users = viewModel.userRes.value
	var balance by remember { mutableStateOf(0.0) }
	var user by remember { mutableStateOf(listOf<User>()) }
	val mUserViewModel: UserViewModel =
		viewModel(factory = UserViewModel.UserViewModelFactory(context.applicationContext as Application))
	user = mUserViewModel.readUser.observeAsState(emptyList()).value
	LaunchedEffect(key1 = user) {
		if (user.isNotEmpty()) {
			viewModel.getUser(user[0].email)
		}
	}
	if (users.user.isNotEmpty()) {
		balance = users.user[0].user?.balance!!
	}
	Column {
		Column(
			Modifier
				.fillMaxWidth()
				.padding(20.dp),
		) {
			Row(
				Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween,
			) {
				Row(
					modifier = Modifier.animateContentSize(
						animationSpec = tween(
							durationMillis = 500,
							easing = LinearOutSlowInEasing
						)
					)
				) {
					ElevatedAssistChip(onClick = { navController.navigate(Screens.Recharge.route) }, label = {
						Row(
							modifier = Modifier.animateContentSize(
								animationSpec = tween(
									durationMillis = 500,
									easing = LinearOutSlowInEasing
								),
							),
							verticalAlignment = Alignment.CenterVertically
						) {
							Text(
								text = "Balance "
							)
							if (users.isLoading) {
								CircularProgressIndicator(
									modifier = Modifier
										.height(16.dp)
										.width(16.dp)
								)
							} else Text("$balance")
							
						}
					})
				}
				Row {
					Text(
						text = "Profile",
						textAlign = TextAlign.Center,
						color = MaterialTheme.colorScheme.secondary,
						modifier = Modifier.clickable { navController.navigate(Screens.Profile.route) },
					)
					Icon(imageVector = Icons.Filled.Person, contentDescription = "Profile")
				}
			}
		}
		Column(
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center,
			modifier = Modifier.fillMaxSize()
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
			OutlinedButton(onClick = { navController.navigate(Screens.PList.route) }) {
				Text(text = "Payment list")
			}
		}
		
	}
}
