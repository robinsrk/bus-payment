package com.example.buspayment.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun addUser(user: User)
	
	@Query("Select * from user")
	fun readUser(): LiveData<List<User>>
	
	@Query("DELETE FROM user")
	fun deleteUsers()
	
}