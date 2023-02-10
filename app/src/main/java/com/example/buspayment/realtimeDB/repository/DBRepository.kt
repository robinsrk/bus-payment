package com.example.buspayment.realtimeDB.repository

import com.example.buspayment.realtimeDB.responses.RealtimeBusResponse
import com.example.buspayment.realtimeDB.responses.RealtimeDistanceResponse
import com.example.buspayment.realtimeDB.responses.RealtimeUserResponse
import com.example.buspayment.utils.ResultState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class DBRepository @Inject constructor(
	private val db: DatabaseReference
) : Repository {
	override fun addUser(user: RealtimeUserResponse.UserResponse): Flow<ResultState<String>> =
		callbackFlow {
			trySend(ResultState.Loading)
			db.child("userList").push().setValue(
				user
			).addOnCompleteListener {
				if (it.isSuccessful)
					trySend(ResultState.Success("Data inserted successfully"))
			}.addOnFailureListener {
				trySend(ResultState.Failure(it))
			}
			awaitClose {
				close()
			}
		}
	
	
	override fun getUser(): Flow<ResultState<List<RealtimeUserResponse>>> = callbackFlow {
		trySend(ResultState.Loading)
		
		val valueEvent = object : ValueEventListener {
			override fun onDataChange(snapshot: DataSnapshot) {
				val users = snapshot.children.map {
					RealtimeUserResponse(
						it.getValue(RealtimeUserResponse.UserResponse::class.java),
						key = it.key
					)
				}
				trySend(ResultState.Success(users))
			}
			
			override fun onCancelled(error: DatabaseError) {
				trySend(ResultState.Failure(error.toException()))
			}
			
		}
		
		db.child("userList").addValueEventListener(valueEvent)
		awaitClose {
			db.child("userList").removeEventListener(valueEvent)
			close()
		}
	}
	
	override fun delete(key: String): Flow<ResultState<String>> = callbackFlow {
		trySend(ResultState.Loading)
		db.child("userList").child(key).removeValue()
			.addOnCompleteListener {
				trySend((ResultState.Success("Data deleted successfully")))
			}
			.addOnFailureListener {
				trySend(ResultState.Failure(it))
			}
		awaitClose {
			close()
		}
	}
	
	override fun updateUser(res: RealtimeUserResponse): Flow<ResultState<String>> = callbackFlow {
		trySend(ResultState.Loading)
		val map = HashMap<String, Any>()
		map["userName"] = res.user?.userName!!
		map["email"] = res.user.email
		map["phone"] = res.user.phone
		map["role"] = res.user.role
		db.child("userList").child(res.key!!).updateChildren(
			map
		).addOnCompleteListener {
			trySend(ResultState.Success("Data updated successfully"))
		}.addOnFailureListener {
			trySend(ResultState.Failure(it))
		}
		awaitClose {
			close()
		}
	}
	
	override fun addBus(bus: RealtimeBusResponse.BusResponse): Flow<ResultState<String>> =
		callbackFlow {
			trySend(ResultState.Loading)
			db.child("busList").push().setValue(
				bus
			).addOnCompleteListener {
				if (it.isSuccessful)
					trySend(ResultState.Success("Data inserted successfully"))
			}.addOnFailureListener {
				trySend(ResultState.Failure(it))
			}
			awaitClose {
				close()
			}
		}
	
	override fun getDistance(): Flow<ResultState<List<RealtimeDistanceResponse>>> = callbackFlow {
		trySend(ResultState.Loading)
		
		val valueEvent = object : ValueEventListener {
			override fun onDataChange(snapshot: DataSnapshot) {
				val distance = snapshot.children.map {
					RealtimeDistanceResponse(
						it.getValue(RealtimeDistanceResponse.DistanceResponse::class.java),
						key = it.key
					)
				}
				trySend(ResultState.Success(distance))
			}
			
			override fun onCancelled(error: DatabaseError) {
				trySend(ResultState.Failure(error.toException()))
			}
			
		}
		
		db.child("distance").addValueEventListener(valueEvent)
		awaitClose {
			db.child("distance").removeEventListener(valueEvent)
			close()
		}
	}
	
	override fun getBus(): Flow<ResultState<List<RealtimeBusResponse>>> = callbackFlow {
		trySend(ResultState.Loading)
		
		val valueEvent = object : ValueEventListener {
			override fun onDataChange(snapshot: DataSnapshot) {
				val buses = snapshot.children.map {
					RealtimeBusResponse(
						it.getValue(RealtimeBusResponse.BusResponse::class.java),
						key = it.key
					)
				}
				trySend(ResultState.Success(buses))
			}
			
			override fun onCancelled(error: DatabaseError) {
				trySend(ResultState.Failure(error.toException()))
			}
			
		}
		
		db.child("busList").addValueEventListener(valueEvent)
		awaitClose {
			db.child("busList").removeEventListener(valueEvent)
			close()
		}
	}
}