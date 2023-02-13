package com.example.buspayment.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
	@PrimaryKey(autoGenerate = true)
	val id: Int,
	val name: String,
	val userId: String,
	val email: String,
	val phone: String,
	val role: String,
	val balance: Double
)