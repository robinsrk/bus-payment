package com.example.buspayment.screens

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.buspayment.data.User
import com.example.buspayment.data.UserViewModel
import com.example.buspayment.navigations.Screens
import com.example.buspayment.ui.theme.LogoImage

@Composable
fun HomeScreen(navController: NavController) {
	val context = LocalContext.current
	var name by remember { mutableStateOf("") }
	var email by remember { mutableStateOf("") }
	var user by remember { mutableStateOf(listOf<User>()) }
	val mUserViewModel: UserViewModel =
		viewModel(factory = UserViewModel.UserViewModelFactory(context.applicationContext as Application))
	user = mUserViewModel.readUser.observeAsState(listOf()).value
	if (user.isNotEmpty()) {
		email = user[0].email
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
				Row {
					Text(text = "Balance")
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
			OutlinedButton(onClick = { navController.navigate(Screens.Scan.route) }) {
				Text(text = "Scan for new bus")
			}
			OutlinedButton(onClick = {
				mUserViewModel.deleteUsers()
				Toast.makeText(context, "Successfully logged out", Toast.LENGTH_LONG).show()
				navController.navigate(Screens.Login.route) {
					popUpTo(0)
				}
			}) {
				Text(text = "Logout")
			}
		}

	}
}

@Preview(showSystemUi = true)
@Composable
fun Preview() {
	HomeScreen(navController = rememberNavController())
}