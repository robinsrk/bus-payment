package com.example.buspayment.realtimeDB.responses

data class RealtimeDistanceResponse(
	val dist: DistanceResponse?,
	val key: String?,
) {
	data class DistanceResponse(
		val name: String = "",
		val value: Double = 0.0,
	)
}
