package com.example.buspayment.screens.admin

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.buspayment.navigations.Screens
import com.example.buspayment.ui.theme.LogoImage

@Composable
fun AdminHomeScreen(navController: NavController) {
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
					Text(text = "Admin")
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
			OutlinedButton(onClick = { navController.navigate(Screens.MAccount.route) }) {
				Text(text = "Manager accounts")
			}
		}
		
	}
}
