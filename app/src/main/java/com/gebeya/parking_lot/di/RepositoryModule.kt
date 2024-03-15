package com.gebeya.parking_lot.di

import com.gebeya.parking_lot.data.directions.DirectionsApi
import com.gebeya.parking_lot.data.directions.DirectionsRepository
import com.gebeya.parking_lot.data.keystore.KeyStoreAccessor
import com.gebeya.parking_lot.data.keystore.KeystoreRepositoryImpl
import com.gebeya.parking_lot.data.network.api.DriverApi
import com.gebeya.parking_lot.data.network.api.LotApi
import com.gebeya.parking_lot.data.network.api.OpApi
import com.gebeya.parking_lot.data.network.api.UserApi
import com.gebeya.parking_lot.data.network.api.VehicleApi
import com.gebeya.parking_lot.data.network.repository.DriverRepositoryImpl
import com.gebeya.parking_lot.data.network.repository.LotRepository
import com.gebeya.parking_lot.data.network.repository.OpRepository
import com.gebeya.parking_lot.data.network.repository.UserRepositoryImpl
import com.gebeya.parking_lot.data.network.repository.VehicleRepositoryImpl
import com.gebeya.parking_lot.data.place.PlaceApi
import com.gebeya.parking_lot.data.place.PlaceRepository
import com.gebeya.parking_lot.domain.repository.DriverRepository
import com.gebeya.parking_lot.domain.repository.KeystoreRepository
import com.gebeya.parking_lot.domain.repository.UserRepository
import com.gebeya.parking_lot.domain.repository.VehicleRepository
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


    @Provides
    @Singleton
    fun providePlaceRepository(placeApi: PlaceApi): PlaceRepository {
        return PlaceRepository(placeApi)
    }


    @Provides
    @Singleton
    fun provideDirectionsRepository(directionsApi: DirectionsApi): DirectionsRepository{
        return DirectionsRepository(directionsApi)
    }


    @Provides
    @Singleton
    fun providesKeyStoreRepository(keyStoreAccessor: KeyStoreAccessor): KeystoreRepository {
        return KeystoreRepositoryImpl(keyStoreAccessor)
    }

    @Provides
    @Singleton
    fun providesDriverRepository(driverApi: DriverApi): DriverRepository {
        return DriverRepositoryImpl(driverApi)
    }

    @Provides
    @Singleton
    fun providesLotRepository(lotApi: LotApi): LotRepository {
        return LotRepository(lotApi)
    }

    @Provides
    @Singleton
    fun providesOpRepository(opApi: OpApi): OpRepository {
        return OpRepository(opApi)
    }

    @Provides
    @Singleton
    fun providesVehicleRepository(vehicleApi: VehicleApi): VehicleRepository {
        return VehicleRepositoryImpl(vehicleApi)
    }
}