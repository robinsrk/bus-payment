package com.example.buspayment.db
//
//import android.content.Context
//import androidx.room.Room
//import com.example.buspayment.data.UserDatabase
//import com.google.android.datatransport.runtime.dagger.Module
//import com.google.android.datatransport.runtime.dagger.Provides
//import javax.inject.Singleton
//
//annotation class ApplicationContext
//
//annotation class InstallIn(val value: Any)
//
//@Module
//@InstallIn(SingletonComponent::class)
//object DatabaseModule {
//	@Singleton
//	@Provides
//	fun ProvideDatabase(
//		@ApplicationContext context: Context
//	) = Room.databaseBuilder(
//		context,
//		UserDatabase::class.java,
//		"my_database"
//	).build()
//
//	@Singleton
//	@Provides
//
//	fun provideDao(database: UserDatabase) = database.userDao()
//}