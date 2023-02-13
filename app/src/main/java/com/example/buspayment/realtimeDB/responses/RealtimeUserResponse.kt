package com.example.buspayment.realtimeDB.responses

data class RealtimeUserResponse(
	val user: UserResponse?,
	val key: String? = ""
) {
	
	data class UserResponse(
		val userName: String = "",
		val userId: String = "",
		val email: String = "",
		val phone: String = "",
		var balance: Double = 0.0,
		val pendingPayment: Double = 0.0,
		val role: String = ""
	)
	
}
