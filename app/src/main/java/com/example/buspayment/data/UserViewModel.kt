package com.example.buspayment.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UserViewModel(application: Application) : AndroidViewModel(application) {
	val readUser: LiveData<List<User>>
	private val repository: UserRepository
	
	init {
		val userDao = UserDatabase.getDatabase(application).userDao()
		repository = UserRepository(userDao)
		readUser = repository.readUser
	}
	
	fun addUser(user: User) {
		viewModelScope.launch(Dispatchers.IO) {
			repository.addUser(user)
		}
	}
	
	fun deleteUsers() {
		viewModelScope.launch(Dispatchers.IO) {
			repository.deleteUsers()
		}
	}
	
	class UserViewModelFactory(
		private val application: Application
	) : ViewModelProvider.Factory {
		override fun <T : ViewModel> create(modelClass: Class<T>): T {
			@Suppress("UNCHECKED_CAST")
			if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
				return UserViewModel(application) as T
			}
			throw IllegalArgumentException("Unknown ViewModel class")
		}
	}
}
