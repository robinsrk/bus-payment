package com.example.buspayment.data

import javax.inject.Inject

class UserRepository @Inject constructor(
	private val userDao: UserDao
) {
	val readUser = userDao.readUser()
	
	fun addUser(user: User) {
		userDao.addUser(user)
	}
	
	fun deleteUsers() {
		userDao.deleteUsers()
	}
}