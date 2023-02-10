package com.example.buspayment.realtimeDB.responses

data class RealtimePaymentListResponse(
	val payment: PaymentResponse?,
	val key: String? = "",
) {
	data class PaymentResponse(
		val status: String? = "",
		val fromUser: String? = "",
		val toUser: String? = "",
		val from: String? = "",
		val to: String? = "",
		val paid: Double? = 0.0,
	)
}
