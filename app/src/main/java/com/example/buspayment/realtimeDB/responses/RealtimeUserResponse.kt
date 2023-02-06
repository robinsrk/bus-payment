package com.example.buspayment.realtimeDB.responses

data class RealtimeUserResponse(
	val user: UserResponse?,
	val key: String? = ""
) {
	
	data class UserResponse(
		val userName: String = "",
		val email: String = "",
		val phone: String = "",
		val balance: Double = 0.0,
		val role: String = ""
	)
	
}
