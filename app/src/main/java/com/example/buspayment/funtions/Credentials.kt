package com.example.buspayment.funtions

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