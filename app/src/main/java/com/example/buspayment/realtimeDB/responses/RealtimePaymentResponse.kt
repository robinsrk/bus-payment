package com.example.buspayment.realtimeDB.responses

data class RealtimePaymentResponse(
	val payment: PaymentResponse?,
	val key: String? = "",
) {
	data class PaymentResponse(
		val status: String? = "",
		val time: String? = "",
		val date: String? = "",
		val passNum: Int? = 0,
		val fromUser: String? = "",
		val toUser: String? = "",
		val from: String? = "",
		val to: String? = "",
		val paid: Double? = 0.0,
		val bus: String? = "",
		val code: String? = "",
	)
}
