package com.example.buspayment.realtimeDB.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buspayment.realtimeDB.repository.Repository
import com.example.buspayment.realtimeDB.responses.RealtimeBusResponse
import com.example.buspayment.realtimeDB.responses.RealtimeDistanceResponse
import com.example.buspayment.realtimeDB.responses.RealtimePaymentListResponse
import com.example.buspayment.realtimeDB.responses.RealtimeUserResponse
import com.example.buspayment.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealtimeViewModel @Inject constructor(
	private val repo: Repository
) : ViewModel() {
	private val _userRes: MutableState<UserState> = mutableStateOf(UserState())
	val userRes: State<UserState> = _userRes
	private val _distRes: MutableState<DistState> = mutableStateOf(DistState())
	val distRes: State<DistState> = _distRes
	private val _busRes: MutableState<BusState> = mutableStateOf(BusState())
	val busRes: State<BusState> = _busRes
	private val _payRes: MutableState<PaymentState> = mutableStateOf(PaymentState())
	val payres: State<PaymentState> = _payRes
	fun addUser(users: RealtimeUserResponse.UserResponse) = repo.addUser(users)
	fun submitPayment(payment: RealtimePaymentListResponse.PaymentResponse) =
		repo.submitPayment(payment)
	
	fun addBus(bus: RealtimeBusResponse.BusResponse) = repo.addBus(bus)
	
	init {
		viewModelScope.launch {
			repo.getUser().collect {
				when (it) {
					is ResultState.Success -> {
						_userRes.value = UserState(
							user = it.data
						)
					}
					
					is ResultState.Failure -> {
						_userRes.value = UserState(
							error = it.msg.toString()
						)
					}
					
					is ResultState.Loading -> {
						_userRes.value = UserState(
							isLoading = true
						)
					}
				}
			}
		}
		viewModelScope.launch {
			repo.getBus().collect {
				when (it) {
					is ResultState.Success -> {
						_busRes.value = BusState(
							bus = it.data
						)
					}
					
					is ResultState.Failure -> {
						_busRes.value = BusState(
							error = it.msg.toString()
						)
					}
					
					is ResultState.Loading -> {
						_busRes.value = BusState(
							isLoading = true
						)
					}
				}
			}
		}
		viewModelScope.launch {
			repo.getConductorPaymentList().collect {
				when (it) {
					is ResultState.Success -> {
						_payRes.value = PaymentState(
							payment = it.data
						)
					}
					
					is ResultState.Failure -> {
						_payRes.value = PaymentState(
							error = it.msg.toString()
						)
					}
					
					is ResultState.Loading -> {
						_payRes.value = PaymentState(
							isLoading = true
						)
					}
				}
			}
		}
		viewModelScope.launch {
			repo.getDistance().collect {
				when (it) {
					is ResultState.Success -> {
						_distRes.value = DistState(
							dist = it.data
						)
					}
					
					is ResultState.Failure -> {
						_busRes.value = BusState(
							error = it.msg.toString()
						)
					}
					
					is ResultState.Loading -> {
						_busRes.value = BusState(
							isLoading = true
						)
					}
				}
			}
		}
	}
	
	fun delete(key: String) = repo.deleteUser(key)
	fun updateUser(user: RealtimeUserResponse) = repo.updateUser(user)
}

data class UserState(
	val user: List<RealtimeUserResponse> = emptyList(),
	val error: String = "",
	val isLoading: Boolean = false
)

data class BusState(
	val bus: List<RealtimeBusResponse> = emptyList(),
	val error: String = "",
	val isLoading: Boolean = false
)

data class DistState(
	val dist: List<RealtimeDistanceResponse> = emptyList(),
	val error: String = "",
	val isLoading: Boolean = false
)

data class PaymentState(
	val payment: List<RealtimePaymentListResponse> = emptyList(),
	val error: String = "",
	val isLoading: Boolean = false
)
