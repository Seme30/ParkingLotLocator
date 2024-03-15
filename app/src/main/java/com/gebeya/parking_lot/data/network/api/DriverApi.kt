package com.gebeya.parking_lot.data.network.api

import com.gebeya.parking_lot.data.network.model.Driver
import com.gebeya.parking_lot.data.network.model.DriverResponse
import com.gebeya.parking_lot.data.network.model.Park
import com.gebeya.parking_lot.data.network.model.Provider
import com.gebeya.parking_lot.data.network.model.ProviderResponse
import com.gebeya.parking_lot.data.network.model.Vehicle
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface DriverApi {
    @POST("parking-lot/drivers")
    suspend fun createDriver(@Body driver: Driver): DriverResponse

    @POST("parking-lot/providers")
    suspend fun createProvider(@Body provider: Provider): ProviderResponse

    @GET("parking-lot/drivers/my")
    suspend fun getDriver(
        @Header("Authorization") token: String,
    ): Driver

    @GET("geo-location/get-parking-lots")
    suspend fun getParks(
        @Header("Authorization") token: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): List<Park>


    @GET("parking-lot/providers/my")
    suspend fun getProvider(
        @Header("Authorization") token: String,
    ): Provider

    @PATCH("parking-lot/providers/{id}")
    suspend fun updateProvider(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body provider: Provider
    ): Provider

    @PATCH("parking-lot/drivers/{id}")
    suspend fun updateDriver(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body driver: Driver
    ): Driver



}