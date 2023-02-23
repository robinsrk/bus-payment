package com.example.buspayment.realtimeDB.repository

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import com.example.buspayment.funtions.NotificationService
import com.example.buspayment.realtimeDB.responses.RealtimeBusResponse
import com.example.buspayment.realtimeDB.responses.RealtimeDistanceResponse
import com.example.buspayment.realtimeDB.responses.RealtimePaymentResponse
import com.example.buspayment.realtimeDB.responses.RealtimeUserResponse
import com.example.buspayment.realtimeDB.responses.RealtimeWithdrawResponse
import com.example.buspayment.utils.ResultState
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class DBRepository @Inject constructor(
	private val db: DatabaseReference
) : Repository, Application() {
	companion object {
		@SuppressLint("StaticFieldLeak")
		private lateinit var context: Context
		fun setContext(con: Context) {
			context = con
		}
	}
	
	val notificationService = NotificationService(context)
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
	
	
	override fun getConductorPaymentList(): Flow<ResultState<List<RealtimePaymentResponse>>> =
		callbackFlow {
			trySend(ResultState.Loading)
			
			val valueEvent = object : ValueEventListener {
				override fun onDataChange(snapshot: DataSnapshot) {
					snapshot.children.map { item ->
						val payment = item.children.map {
							RealtimePaymentResponse(
								it.getValue(RealtimePaymentResponse.PaymentResponse::class.java),
								key = it.key
							)
						}
						trySend(ResultState.Success(payment))
					}
				}
				
				override fun onCancelled(error: DatabaseError) {
					trySend(ResultState.Failure(error.toException()))
				}
				
			}
			
			db.child("paymentList").addValueEventListener(valueEvent)
			awaitClose {
				db.child("paymentList").removeEventListener(valueEvent)
				close()
			}
		}
	
	override fun getPaymentHistoryByUser(email: String): Flow<ResultState<List<RealtimePaymentResponse>>> =
		callbackFlow {
			trySend(ResultState.Loading)
			
			val valueEvent = object : ValueEventListener {
				override fun onDataChange(snapshot: DataSnapshot) {
					val payment = snapshot.children.map {
						RealtimePaymentResponse(
							it.getValue(RealtimePaymentResponse.PaymentResponse::class.java),
							key = it.key
						)
					}
					trySend(ResultState.Success(payment))
				}
				
				
				override fun onCancelled(error: DatabaseError) {
					trySend(ResultState.Failure(error.toException()))
				}
				
			}
			
			val childEvent = object : ChildEventListener {
				override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
					Log.d("Firebase", "Child added")
				}
				
				override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
					val payment = snapshot.children.map {
						it.value
					}
					notificationService.showNotification(
						"Payment ${payment[7]}",
						"Your payment of ${payment[5]} taka from ${payment[3]} to ${payment[9]} has been ${payment[7]}",
					)
				}
				
				override fun onChildRemoved(snapshot: DataSnapshot) {
					Log.d("Firebase", "Child removed")
				}
				
				override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
					Log.d("Firebase", "Child moved")
				}
				
				override fun onCancelled(error: DatabaseError) {
					trySend(ResultState.Failure(error.toException()))
				}
				
			}
			db.child("paymentList").child(email)
				.addValueEventListener(valueEvent)
			db.child("paymentList").child(email)
				.addChildEventListener(childEvent)
			awaitClose {
				db.child("paymentList").child(email)
					.removeEventListener(valueEvent)
				close()
			}
		}
	
	override fun getUser(email: String): Flow<ResultState<List<RealtimeUserResponse>>> =
		callbackFlow {
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
			
			if (email.isNotEmpty()) {
				db.child("userList").orderByChild("email").equalTo(email).addValueEventListener(valueEvent)
			} else {
				db.child("userList").addValueEventListener(valueEvent)
			}
			awaitClose {
				db.child("userList").removeEventListener(valueEvent)
				db.child("userList").orderByChild("email").equalTo(email).removeEventListener(valueEvent)
				close()
			}
		}
	
	override fun deleteUser(key: String): Flow<ResultState<String>> = callbackFlow {
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
		val newUser = HashMap<String, Any>()
		newUser["userName"] = res.user?.userName!!
		newUser["email"] = res.user.email
		newUser["phone"] = res.user.phone
		newUser["role"] = res.user.role
		newUser["balance"] = res.user.balance
		db.child("userList").child(res.key!!).updateChildren(
			newUser
		).addOnCompleteListener {
			trySend(ResultState.Success("Data updated successfully"))
		}.addOnFailureListener {
			trySend(ResultState.Failure(it))
		}
		awaitClose {
			close()
		}
	}
	
	override fun updatePayment(
		email: String,
		res: RealtimePaymentResponse
	): Flow<ResultState<String>> =
		callbackFlow {
			trySend(ResultState.Loading)
			val newUser = HashMap<String, Any>()
			newUser["fromUser"] = res.payment?.fromUser!!
			newUser["toUser"] = res.payment.toUser!!
			newUser["from"] = res.payment.from!!
			newUser["to"] = res.payment.to!!
			newUser["paid"] = res.payment.paid!!
			newUser["bus"] = res.payment.bus!!
			newUser["status"] = res.payment.status!!
			db.child("paymentList").child(email).child(res.key!!).updateChildren(
				newUser
			).addOnCompleteListener {
				trySend(ResultState.Success("Data updated successfully"))
			}.addOnFailureListener {
				trySend(ResultState.Failure(it))
			}
			awaitClose {
				close()
			}
		}
	
	override fun submitPayment(
		payment: RealtimePaymentResponse.PaymentResponse,
		from: String,
		to: String
	): Flow<ResultState<String>> =
		callbackFlow {
			trySend(ResultState.Loading)
			db.child("paymentList").child(from).push().setValue(
				payment
			).addOnCompleteListener {
				if (it.isSuccessful)
					trySend(ResultState.Success("Payment successful"))
			}
				.addOnFailureListener {
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
	
	override fun addDistance(dist: RealtimeDistanceResponse.DistanceResponse): Flow<ResultState<String>> =
		callbackFlow {
			trySend(ResultState.Loading)
			db.child("distance").push().setValue(
				dist
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
	
	override fun addWithdraw(wd: RealtimeWithdrawResponse.WithdrawResponse): Flow<ResultState<String>> =
		callbackFlow {
			trySend(ResultState.Loading)
			db.child("withdraw").push().setValue(
				wd
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
	
	override fun getWithdraw(): Flow<ResultState<List<RealtimeWithdrawResponse>>> =
		callbackFlow {
			trySend(ResultState.Loading)
			
			val valueEvent = object : ValueEventListener {
				override fun onDataChange(snapshot: DataSnapshot) {
					val withdraw = snapshot.children.map {
						RealtimeWithdrawResponse(
							it.getValue(RealtimeWithdrawResponse.WithdrawResponse::class.java),
							key = it.key
						)
					}
					trySend(ResultState.Success(withdraw))
				}
				
				override fun onCancelled(error: DatabaseError) {
					trySend(ResultState.Failure(error.toException()))
				}
				
			}
			
			db.child("withdraw").addValueEventListener(valueEvent)
			awaitClose {
				db.child("withdraw").removeEventListener(valueEvent)
				close()
			}
		}
	
	override fun updateWithdraw(wd: RealtimeWithdrawResponse.WithdrawResponse): Flow<ResultState<String>> {
		TODO("Not yet implemented")
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
	
	override fun updateBalance(pay: Double, from: String, to: String): Flow<ResultState<String>> =
		callbackFlow {
			trySend(ResultState.Loading)
			val paymentTransaction = object : Transaction.Handler {
				override fun doTransaction(currentData: MutableData): Transaction.Result {
					val currentValue = currentData.getValue(RealtimeUserResponse.UserResponse::class.java)
					if (currentValue!!.userId == from)
						currentValue.balance = currentValue.balance.plus(pay)
					else
						currentValue.balance = currentValue.balance.plus(-pay)
					currentData.value = currentValue
					return Transaction.success(currentData)
				}
				
				override fun onComplete(
					error: DatabaseError?,
					committed: Boolean,
					currentData: DataSnapshot?
				) {
					if (error != null) {
						Log.e("Firebase", "Transaction failed")
					} else {
						Log.i("Firebase", "Transaction succeeded")
					}
				}
				
			}
			val singleValueListener = object : ValueEventListener {
				override fun onDataChange(snapshot: DataSnapshot) {
					snapshot.children.forEach { child ->
						Log.d("firebase single value", "adding single value event listener")
						child.ref.runTransaction(paymentTransaction)
					}
				}
				
				override fun onCancelled(error: DatabaseError) {
					trySend(ResultState.Failure(error.toException()))
				}
				
			}
			db.child("userList").orderByChild("userId").equalTo(from)
				.addListenerForSingleValueEvent(singleValueListener)
			if (to.isNotEmpty())
				db.child("userList").orderByChild("userId").equalTo(to)
					.addListenerForSingleValueEvent(singleValueListener)
//			db.child("userList").runTransaction(paymentTransaction)
			awaitClose {
				db.child("userList").orderByChild("userId").equalTo(from)
					.removeEventListener(singleValueListener)
				if (to.isNotEmpty())
					db.child("userList").orderByChild("userId").equalTo(to)
						.removeEventListener(singleValueListener)
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

