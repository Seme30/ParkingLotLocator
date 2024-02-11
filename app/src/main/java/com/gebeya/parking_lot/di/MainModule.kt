package com.gebeya.parking_lot.di

import android.app.Application
import com.gebeya.parking_lot.data.datapreference.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideDataStoreRepository(
        application: Application
    ) = DataStoreRepository(context = application)
}