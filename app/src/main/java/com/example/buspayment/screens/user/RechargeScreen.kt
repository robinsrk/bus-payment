package com.example.buspayment.screens.user

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.buspayment.data.User
import com.example.buspayment.data.UserViewModel
import com.example.buspayment.realtimeDB.ui.RealtimeViewModel
import com.example.buspayment.utils.ResultState
import com.stripe.android.PaymentConfiguration
import kotlinx.coroutines.launch

@Composable
fun RechargeScreen(
	navController: NavController,
	viewModel: RealtimeViewModel = hiltViewModel()
) {
	val context = LocalContext.current
	PaymentConfiguration.init(
		context,
		"pk_test_51Iem6QBbLdAJxHXGLSIrAQ3lqaEGBB3b4ZX7h0FsY21lxW4eIv3tbGeSsuFuKeHocD0nS0NpQGvGvRVe00VazD0N00mTPzU5nw"
	)
	var user by remember { mutableStateOf(listOf<User>()) }
	val mUserViewModel: UserViewModel =
		viewModel(factory = UserViewModel.UserViewModelFactory(context.applicationContext as Application))
	user = mUserViewModel.readUser.observeAsState(emptyList()).value
	val scope = rememberCoroutineScope()
	Column(Modifier.fillMaxSize()) {
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
			Text(text = "Recharge account", style = MaterialTheme.typography.headlineSmall)
			Text("")
		}
		Column(
			Modifier
				.fillMaxSize()
				.padding(50.dp),
			verticalArrangement = Arrangement.SpaceBetween,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Card(
				Modifier
					.height(200.dp)
					.width(200.dp)
					.clickable {
						scope.launch {
							viewModel
								.updateBalance(50.0, user[0].email.substringBefore("@"), "")
								.collect { response ->
									when (response) {
										is ResultState.Success -> {
											Toast
												.makeText(context, "Payment successful", Toast.LENGTH_SHORT)
												.show()
										}
										
										is ResultState.Failure -> {
											Toast
												.makeText(context, "Failed", Toast.LENGTH_SHORT)
												.show()
										}
										
										is ResultState.Loading -> {}
									}
								}
						}
					}
			) {
				Text(
					"50 taka",
					Modifier.fillMaxWidth(),
					textAlign = TextAlign.Center,
					style = MaterialTheme.typography.headlineSmall
				)
			}
			Card(
				Modifier
					.height(200.dp)
					.width(200.dp)
					.clickable {
						scope.launch {
							viewModel
								.updateBalance(100.0, user[0].email.substringBefore("@"), "")
								.collect { response ->
									when (response) {
										is ResultState.Success -> {
											Toast
												.makeText(context, "Payment successful", Toast.LENGTH_SHORT)
												.show()
										}
										
										is ResultState.Failure -> {
											Toast
												.makeText(context, "Failed", Toast.LENGTH_SHORT)
												.show()
										}
										
										is ResultState.Loading -> {}
									}
								}
						}
					}
			) {
				Text(
					"100 taka",
					Modifier.fillMaxWidth(),
					textAlign = TextAlign.Center,
					style = MaterialTheme.typography.headlineSmall
				)
			}
			
			Card(
				Modifier
					.height(200.dp)
					.width(200.dp)
					.clickable {
						scope.launch {
							viewModel
								.updateBalance(500.0, user[0].email.substringBefore("@"), "")
								.collect { response ->
									when (response) {
										is ResultState.Success -> {
											Toast
												.makeText(context, "Payment successful", Toast.LENGTH_SHORT)
												.show()
										}
										
										is ResultState.Failure -> {
											Toast
												.makeText(context, "Failed", Toast.LENGTH_SHORT)
												.show()
										}
										
										is ResultState.Loading -> {}
									}
								}
						}
					}
			) {
				Text(
					"500 taka",
					Modifier.fillMaxWidth(),
					textAlign = TextAlign.Center,
					style = MaterialTheme.typography.headlineSmall
				)
			}
			
		}
		
	}
}