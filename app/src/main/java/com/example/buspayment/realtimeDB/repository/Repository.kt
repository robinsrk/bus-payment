package com.example.buspayment.realtimeDB.repository

import com.example.buspayment.realtimeDB.responses.RealtimeBusResponse
import com.example.buspayment.realtimeDB.responses.RealtimeDistanceResponse
import com.example.buspayment.realtimeDB.responses.RealtimePaymentResponse
import com.example.buspayment.realtimeDB.responses.RealtimeUserResponse
import com.example.buspayment.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface Repository {
	fun addUser(
		user: RealtimeUserResponse.UserResponse
	): Flow<ResultState<String>>
	
	fun getUser(email: String = ""): Flow<ResultState<List<RealtimeUserResponse>>>
	fun getBus(): Flow<ResultState<List<RealtimeBusResponse>>>
	fun getDistance(): Flow<ResultState<List<RealtimeDistanceResponse>>>
	fun addDistance(dist: RealtimeDistanceResponse.DistanceResponse): Flow<ResultState<String>>
	fun updateBalance(pay: Double, from: String, to: String): Flow<ResultState<String>>
	fun submitPayment(
		payment: RealtimePaymentResponse.PaymentResponse,
		from: String,
		to: String
	): Flow<ResultState<String>>
	
	fun getConductorPaymentList(): Flow<ResultState<List<RealtimePaymentResponse>>>
	fun getPaymentHistoryByUser(email: String): Flow<ResultState<List<RealtimePaymentResponse>>>
	fun updatePayment(email: String, res: RealtimePaymentResponse): Flow<ResultState<String>>
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