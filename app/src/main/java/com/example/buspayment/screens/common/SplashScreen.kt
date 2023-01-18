package com.example.buspayment.screens.common

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.buspayment.data.User
import com.example.buspayment.data.UserViewModel
import com.example.buspayment.navigations.Screens
import com.example.buspayment.ui.theme.LogoIcon
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController) {
	val context = LocalContext.current
	var user by remember { mutableStateOf(listOf<User>()) }
	val mUserViewModel: UserViewModel =
		viewModel(factory = UserViewModel.UserViewModelFactory(context.applicationContext as Application))
	user = mUserViewModel.readUser.observeAsState(listOf()).value
	LaunchedEffect(key1 = user) {
		delay(2000)
		if (user.isNotEmpty()) {
//			Credentials().setEmail(user[0].email)
			if (user[0].role == "User") {
				navController.navigate(Screens.UHome.route) {
					popUpTo(0)
				}
			} else if (user[0].role == "Conductor") {
				navController.navigate(Screens.CHome.route) {
					popUpTo(0);
				}
			} else if (user[0].role == "Admin") {
				navController.navigate(Screens.AHome.route) {
					popUpTo(0);
				}
			}
		} else {
			navController.navigate(Screens.Login.route) {
				popUpTo(0)
			}
		}
	}
	Box(contentAlignment = Alignment.Center) {
		Splash()
	}
}


@Composable
fun Splash() {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
		modifier = Modifier.fillMaxSize()
	) {
		Surface {
			Text(
				text = "Loading",
				textAlign = TextAlign.Center,
				style = MaterialTheme.typography.headlineLarge,
				color = MaterialTheme.colorScheme.secondary
			)
		}
		LogoIcon()
		LinearProgressIndicator()
		
	}
}