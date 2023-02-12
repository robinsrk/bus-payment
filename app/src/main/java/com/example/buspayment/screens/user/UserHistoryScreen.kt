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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.buspayment.data.User
import com.example.buspayment.data.UserViewModel
import com.example.buspayment.realtimeDB.responses.RealtimeUserHistoryResponse
import com.example.buspayment.realtimeDB.ui.RealtimeViewModel

@Composable
fun UserHistoryScreen(
	navController: NavController,
	viewModel: RealtimeViewModel = hiltViewModel()
) {
	val context = LocalContext.current
	var email by remember { mutableStateOf("") }
	var user by remember { mutableStateOf(listOf<User>()) }
	val mUserViewModel: UserViewModel =
		viewModel(factory = UserViewModel.UserViewModelFactory(context.applicationContext as Application))
	user = mUserViewModel.readUser.observeAsState(emptyList()).value
	if (user.isNotEmpty()) {
		email = user[0].email
	}
	LaunchedEffect(key1 = true) {
		viewModel.getPaymentHistoryByUser(email = "123")
	}
	val history = viewModel.userHisRes.value
	Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
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
			Text(text = "History", style = MaterialTheme.typography.headlineSmall)
			Text("")
		}
		Column {
			if (history.payment.isNotEmpty()) {
				LazyColumn() {
					items(
						history.payment,
						key = { it.key!! }
					) { res ->
						HistoryRow(res.payment!!)
					}
				}
			} else if (history.isLoading) {
				CircularProgressIndicator()
			}
		}
	}
}

@Composable
fun HistoryRow(
	itemState: RealtimeUserHistoryResponse.PaymentResponse
) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.padding(10.dp)
	) {
		Row(
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier
				.fillMaxWidth()
				.padding(10.dp)
		) {
			Text(itemState.bus!!, style = MaterialTheme.typography.bodyLarge)
			Text("Time")
			Column(horizontalAlignment = Alignment.CenterHorizontally) {
				Text(itemState.paid!!.toString() + " Taka")
				Text("${itemState.from!!} -> ${itemState.to!!}")
			}
			Text(itemState.status!!)
		}
	}
}
