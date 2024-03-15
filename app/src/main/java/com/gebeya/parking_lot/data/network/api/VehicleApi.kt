package com.gebeya.parking_lot.data.network.api

import com.gebeya.parking_lot.data.network.model.Vehicle
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface VehicleApi {


    @GET("parking-lot/vehicles/my")
    suspend fun getVehicles(
        @Header("Authorization") token: String
    ): List<Vehicle>

    @GET("parking-lot/vehicles/{id}")
    suspend fun getVehicleById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Vehicle

    @POST("parking-lot/vehicles")
    suspend fun createVehicle(
        @Header("Authorization") token: String,
        @Body vehicle: Vehicle
    ): Vehicle


    @PATCH("parking-lot/vehicles/{id}")
    suspend fun updateVehicle(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body vehicle: Vehicle
    ): Vehicle


    @DELETE("parking-lot/vehicles/{id}")
    suspend fun deleteVehicle(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    )
}