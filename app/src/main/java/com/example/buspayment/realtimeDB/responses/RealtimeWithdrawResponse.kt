package com.example.buspayment.realtimeDB.responses

data class RealtimeWithdrawResponse(
	val withDraw: WithdrawResponse?,
	val key: String? = ""
) {
	data class WithdrawResponse(
		val from: String? = "",
		val amount: Double? = 0.0,
		val status: String? = "",
	)
}
