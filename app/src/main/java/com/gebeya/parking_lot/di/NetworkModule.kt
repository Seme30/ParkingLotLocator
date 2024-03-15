package com.gebeya.parking_lot.di

import com.gebeya.parking_lot.data.datapreference.DataStoreRepository
import com.gebeya.parking_lot.data.directions.DirectionsApi
import com.gebeya.parking_lot.data.network.api.DriverApi
import com.gebeya.parking_lot.data.network.api.LotApi
import com.gebeya.parking_lot.data.network.api.OpApi
import com.gebeya.parking_lot.data.network.api.UserApi
import com.gebeya.parking_lot.data.network.api.VehicleApi
import com.gebeya.parking_lot.data.network.model.Vehicle
import com.gebeya.parking_lot.data.place.PlaceApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun providesUserApi(client: OkHttpClient): UserApi {
        return Retrofit.Builder()
            .baseUrl("https://a4ac-196-189-16-208.ngrok-free.app/api/v1/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(client)
            .build()
            .create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun providesVehicleApi(client: OkHttpClient): VehicleApi {
        return Retrofit.Builder()
            .baseUrl("https://a4ac-196-189-16-208.ngrok-free.app/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(VehicleApi::class.java)
    }



    @Provides
    @Singleton
    fun providesLotApi(client: OkHttpClient): LotApi {
        return Retrofit.Builder()
            .baseUrl("https://a4ac-196-189-16-208.ngrok-free.app/api/v1/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(client)
            .build()
            .create(LotApi::class.java)
    }

    @Provides
    @Singleton
    fun providesOpApi(client: OkHttpClient): OpApi {
        return Retrofit.Builder()
            .baseUrl("https://a4ac-196-189-16-208.ngrok-free.app/api/v1/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(client)
            .build()
            .create(OpApi::class.java)
    }


    @Provides
    @Singleton
    fun providesDriverApi(client: OkHttpClient): DriverApi {
        return Retrofit.Builder()
            .baseUrl("https://a4ac-196-189-16-208.ngrok-free.app/api/v1/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(client)
            .build()
            .create(DriverApi::class.java)
    }


    @Provides
    @Singleton
    fun providesPlaceApiService(): PlaceApi {

        return Retrofit.Builder()
            .baseUrl("https://nominatim.openstreetmap.org/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(PlaceApi::class.java)
    }


    @Provides
    @Singleton
    fun providesDirectionService(): DirectionsApi {

        return Retrofit.Builder()
            .baseUrl("https://api.openrouteservice.org/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(DirectionsApi::class.java)
    }
}