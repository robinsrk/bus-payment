package com.example.buspayment.realtimeDB.repository

import com.example.buspayment.realtimeDB.responses.RealtimeBusResponse
import com.example.buspayment.realtimeDB.responses.RealtimeDistanceResponse
import com.example.buspayment.realtimeDB.responses.RealtimeUserHistoryResponse
import com.example.buspayment.realtimeDB.responses.RealtimeUserResponse
import com.example.buspayment.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface Repository {
	fun addUser(
		user: RealtimeUserResponse.UserResponse
	): Flow<ResultState<String>>
	
	fun getUser(): Flow<ResultState<List<RealtimeUserResponse>>>
	fun getBus(): Flow<ResultState<List<RealtimeBusResponse>>>
	fun getDistance(): Flow<ResultState<List<RealtimeDistanceResponse>>>
	fun submitPayment(
		payment: RealtimeUserHistoryResponse.PaymentResponse,
		email: String
	): Flow<ResultState<String>>
	
	fun getConductorPaymentList(email: String): Flow<ResultState<List<RealtimeUserHistoryResponse>>>
	fun getPaymentHistoryByUser(email: String): Flow<ResultState<List<RealtimeUserHistoryResponse>>>
	fun updatePayment(res: RealtimeUserHistoryResponse): Flow<ResultState<String>>
	fun deleteUser(
		key: String
	): Flow<ResultState<String>>
	
	fun addBus(
		bus: RealtimeBusResponse.BusResponse
	): Flow<ResultState<String>>
	
	fun updateUser(
		res: RealtimeUserResponse
	): Flow<ResultState<String>>
}