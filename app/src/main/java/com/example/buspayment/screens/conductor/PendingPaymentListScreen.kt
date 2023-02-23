@file:OptIn(ExperimentalFoundationApi::class)

package com.example.buspayment.screens.conductor

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.buspayment.realtimeDB.responses.RealtimePaymentResponse
import com.example.buspayment.realtimeDB.ui.RealtimeViewModel
import com.example.buspayment.utils.ResultState
import kotlinx.coroutines.launch

@Composable
fun PaymentListScreen(
	navController: NavController,
	viewModel: RealtimeViewModel = hiltViewModel()
) {
	val res = viewModel.payres.value
	val context = LocalContext.current
	var user by remember { mutableStateOf(listOf<User>()) }
	val mUserViewModel: UserViewModel =
		viewModel(factory = UserViewModel.UserViewModelFactory(context.applicationContext as Application))
	user = mUserViewModel.readUser.observeAsState(emptyList()).value
	var email by remember { mutableStateOf("") }
	LaunchedEffect(user) {
		if (user.isNotEmpty()) {
			email = user[0].email
			viewModel.getConductorPaymentList(user[0].email.substringBefore("@"))
		}
		
	}
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
			Text(text = "Pending payments", style = MaterialTheme.typography.headlineSmall)
			Icon(
				modifier = Modifier.clickable {
					navController.popBackStack()
				}, imageVector = Icons.Filled.ArrowForward, contentDescription = "Back button"
			)
		}
		Column {
			if (res.payment.isNotEmpty()) {
				val paymentList =
					res.payment.filter { item ->
						item.payment!!.status == "Pending" && item.payment.toUser == email.substringBefore(
							"@"
						)
					}
				LazyColumn(reverseLayout = true) {
					items(
						paymentList,
						key = {
							it.key!!
						}
					) { res ->
						PaymentListRow(itemState = res)
					}
				}
			} else if (res.isLoading) {
				CircularProgressIndicator()
			}
		}
	}
}

@Composable
fun PaymentListRow(
	itemState: RealtimePaymentResponse,
	viewModel: RealtimeViewModel = hiltViewModel()
) {
	val scope = rememberCoroutineScope()
	val context = LocalContext.current
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.padding(10.dp)
	) {
		Column() {
			Row(
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier
					.fillMaxWidth()
					.padding(10.dp)
			) {
				Text(itemState.payment?.fromUser!!, style = MaterialTheme.typography.bodyLarge)
				Text(itemState.payment.time.toString())
				Column(horizontalAlignment = Alignment.CenterHorizontally) {
					Text(itemState.payment.paid!!.toString() + " Taka")
					Text("${itemState.payment.from!!} -> ${itemState.payment.to!!}")
				}
				Row() {
					Icon(imageVector = Icons.Filled.Done, contentDescription = "Accept",
						modifier = Modifier.clickable {
							scope.launch {
								viewModel.updatePayment(
									email = itemState.payment.fromUser,
									RealtimePaymentResponse(
										payment = RealtimePaymentResponse.PaymentResponse(
											fromUser = itemState.payment.fromUser,
											code = itemState.payment.code,
											status = "Accepted",
											toUser = itemState.payment.toUser,
											from = itemState.payment.from,
											to = itemState.payment.to,
											paid = itemState.payment.paid,
											bus = itemState.payment.bus
										),
										key = itemState.key
									)
								).collect {
									when (it) {
										is ResultState.Success -> {
											Toast.makeText(context, "Payment accepted", Toast.LENGTH_SHORT).show()
										}
										
										is ResultState.Failure -> {
											Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
										}
										
										is ResultState.Loading -> {
										
										}
									}
								}
							}
						}
					)
					Icon(imageVector = Icons.Filled.Close, contentDescription = "Reject",
						modifier = Modifier.clickable {
							scope.launch {
								viewModel.updatePayment(
									email = itemState.payment.fromUser,
									RealtimePaymentResponse(
										payment = RealtimePaymentResponse.PaymentResponse(
											fromUser = itemState.payment.fromUser,
											code = itemState.payment.code,
											status = "Rejected",
											toUser = itemState.payment.toUser,
											from = itemState.payment.from,
											to = itemState.payment.to,
											paid = itemState.payment.paid,
											bus = itemState.payment.bus
										),
										key = itemState.key
									)
								).collect {
									when (it) {
										is ResultState.Success -> {
											Toast.makeText(context, "Payment rejected", Toast.LENGTH_SHORT).show()
										}
										
										is ResultState.Failure -> {
											Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
										}
										
										is ResultState.Loading -> {
										
										}
									}
								}
							}
						}
					)
				}
			}
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.Center
			)
			{
				Text(text = itemState.payment!!.code!!, style = MaterialTheme.typography.headlineMedium)
			}
		}
	}
}