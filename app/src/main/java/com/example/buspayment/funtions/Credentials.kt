package com.example.buspayment.funtions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class Credentials() {
	private var name: String = ""
	private var email: String = ""
	fun getUser(): String {
		return name
	}

	fun setUser(username: String) {
		this.name = username
	}

	fun setEmail(email: String) {
		this.email = email
	}

	fun getEmail(): String {
		return email
	}

}

@Composable
fun Info() {
	var email by remember { mutableStateOf("") }
	fun getEmail() = email
	fun setEmail(mail: String) {
		email = mail
	}
}