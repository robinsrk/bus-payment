package com.example.buspayment.realtimeDB.responses

data class RealtimeBusResponse(
	val bus: BusResponse?,
	val key: String? = ""
) {
	
	data class BusResponse(
		val name: String = "",
		val id: String = "",
		val startAddress: String = "",
		val endAddress: String = "",
	)
}
