package com.example.buspayment.screens.user

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.buspayment.data.User
import com.example.buspayment.data.UserViewModel
import com.example.buspayment.navigations.Screens
import com.example.buspayment.ui.theme.LogoImage

@Composable
fun UserHomeScreen(
	navController: NavController,
) {
	val context = LocalContext.current
	var balance by remember { mutableStateOf(0.0) }
	var user by remember { mutableStateOf(listOf<User>()) }
	val mUserViewModel: UserViewModel =
		viewModel(factory = UserViewModel.UserViewModelFactory(context.applicationContext as Application))
	user = mUserViewModel.readUser.observeAsState(emptyList()).value
	if (user.isNotEmpty()) {
		balance = user[0].balance
	}
	Column {
		Column(
			Modifier
				.fillMaxWidth()
		) {
			Row(
				Modifier
					.fillMaxWidth()
					.background(Color.Red, RoundedCornerShape(0.dp, 0.dp, 30.dp, 30.dp))
					.padding(20.dp),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically
			) {
				Row {
					Text(
						text = "Balance $balance",
						Modifier.clickable { navController.navigate(Screens.Recharge.route) })
				}
				Text("", style = MaterialTheme.typography.headlineSmall)
				Row {
					Text(
						text = "Profile",
						textAlign = TextAlign.Center,
//						color = MaterialTheme.colorScheme.secondary,
						modifier = Modifier.clickable { navController.navigate(Screens.Profile.route) },
					)
					Icon(
						imageVector = Icons.Filled.Person,
						contentDescription = "Profile",
					)
					
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
			OutlinedButton(
				onClick = {
					navController.navigate(Screens.Scan.route)
				}
			) {
				Text(text = "Scan for new bus")
			}
			OutlinedButton(
				onClick = {
					navController.navigate(Screens.UHistory.route)
				},
			) {
				Text(text = "Check history")
			}
		}
		
	}
}
