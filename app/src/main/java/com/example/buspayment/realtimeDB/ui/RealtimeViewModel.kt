package com.example.buspayment.realtimeDB.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buspayment.realtimeDB.repository.Repository
import com.example.buspayment.realtimeDB.responses.RealtimeBusResponse
import com.example.buspayment.realtimeDB.responses.RealtimeUserResponse
import com.example.buspayment.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealtimeViewModel @Inject constructor(
	private val repo: Repository
) : ViewModel() {
	private val _res: MutableState<UserState> = mutableStateOf(UserState())
	val res: State<UserState> = _res
	fun addUser(users: RealtimeUserResponse.UserResponse) = repo.addUser(users)
	fun addBus(bus: RealtimeBusResponse.BusResponse) = repo.addBus(bus)
	
	init {
		viewModelScope.launch {
			repo.getUser().collect {
				when (it) {
					is ResultState.Success -> {
						_res.value = UserState(
							user = it.data
						)
					}
					
					is ResultState.Failure -> {
						_res.value = UserState(
							error = it.msg.toString()
						)
					}
					
					is ResultState.Loading -> {
						_res.value = UserState(
							isLoading = true
						)
					}
				}
			}
		}
	}
	
	fun delete(key: String) = repo.delete(key)
	fun updateUser(user: RealtimeUserResponse) = repo.updateUser(user)
}

data class UserState(
	val user: List<RealtimeUserResponse> = emptyList(),
	val error: String = "",
	val isLoading: Boolean = false
)