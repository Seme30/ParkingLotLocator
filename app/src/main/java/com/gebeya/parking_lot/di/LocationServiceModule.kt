package com.gebeya.parking_lot.di

import android.app.Application
import com.gebeya.parking_lot.data.location.LocationServiceRepositoryImpl
import com.gebeya.parking_lot.domain.repository.LocationServiceRepository
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationServiceModule {

    @Singleton
    @Provides
    fun provideLocationServiceRepository(app: Application): LocationServiceRepository {
        return LocationServiceRepositoryImpl(
            LocationServices.getFusedLocationProviderClient(app)
        )
    }

}