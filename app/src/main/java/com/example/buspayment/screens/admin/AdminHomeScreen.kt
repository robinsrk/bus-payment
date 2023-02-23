package com.example.buspayment.screens.admin

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
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.buspayment.data.UserViewModel
import com.example.buspayment.navigations.Screens
import com.example.buspayment.ui.theme.LogoImage

@Composable
fun AdminHomeScreen(navController: NavController) {
	val context = LocalContext.current
	val mUserViewModel: UserViewModel =
		viewModel(factory = UserViewModel.UserViewModelFactory(context.applicationContext as Application))
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
				Text(text = "Admin")
				Icon(
					imageVector = Icons.Default.Logout,
					contentDescription = "Forward button",
					modifier = Modifier.clickable {
						mUserViewModel.deleteUsers()
						Toast.makeText(context, "Successfully logged out", Toast.LENGTH_LONG).show()
						navController.navigate(Screens.Login.route) {
							popUpTo(0)
						}
					}
				)
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
			OutlinedButton(onClick = { navController.navigate(Screens.MBus.route) }) {
				Text(text = "Manager buses")
			}
			OutlinedButton(onClick = { navController.navigate(Screens.MDist.route) }) {
				Text(text = "Manager locations")
			}
		}
		
	}
}
