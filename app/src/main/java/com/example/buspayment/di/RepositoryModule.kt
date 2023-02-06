package com.example.buspayment.di

import com.example.buspayment.realtimeDB.repository.DBRepository
import com.example.buspayment.realtimeDB.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
	@Binds
	abstract fun provideRepository(
		repo: DBRepository
	): Repository
}