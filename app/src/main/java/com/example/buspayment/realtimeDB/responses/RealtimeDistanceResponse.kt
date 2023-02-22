package com.example.buspayment.realtimeDB.responses

data class RealtimeDistanceResponse(
	val dist: DistanceResponse?,
	val key: String?,
) {
	data class DistanceResponse(
		val name: String = "",
		val value: Double = 0.0,
		val lat1: Double = 0.0,
		val lng1: Double = 0.0,
		val lat2: Double = 0.0,
		val lng2: Double = 0.0,
		val lat3: Double = 0.0,
		val lng3: Double = 0.0,
		val lat4: Double = 0.0,
		val lng4: Double = 0.0,
	)
}
