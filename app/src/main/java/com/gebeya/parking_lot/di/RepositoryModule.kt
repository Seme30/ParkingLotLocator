package com.gebeya.parking_lot.di

import com.gebeya.parking_lot.data.network.api.UserApi
import com.gebeya.parking_lot.data.network.repository.UserRepositoryImpl
import com.gebeya.parking_lot.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    @Singleton
    fun provideUserRepository(userApi: UserApi): UserRepository {
        return UserRepositoryImpl(userApi)
    }
}