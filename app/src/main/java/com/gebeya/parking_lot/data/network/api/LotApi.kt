package com.gebeya.parking_lot.data.network.api

import com.gebeya.parking_lot.data.network.model.Lot
import com.gebeya.parking_lot.data.network.model.LotResponse
import com.gebeya.parking_lot.data.network.model.Reserve
import com.gebeya.parking_lot.data.network.model.TimeItem
import com.gebeya.parking_lot.data.network.model.Vehicle
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface LotApi {

    @POST("parking-lot/lots")
    suspend fun createLot(
        @Header("Authorization") token: String,
        @Body lot: Lot
    ): LotResponse


    @GET("parking-lot/lots/{id}")
    suspend fun getLotById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): LotResponse
    @GET("parking-lot/lots/my")
    suspend fun getLot(
        @Header("Authorization") token: String,
    ): LotResponse


    @PATCH("parking-lot/lots/{id}")
    suspend fun updateLot(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body lot: Lot
    ): LotResponse


    @DELETE("parking-lot/lots/{id}")
    suspend fun deleteLot(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    )

    @POST("parking-lot/lots/{id}/reservations")
    suspend fun addReserve(
        @Header("Authorization") token: String,
        @Path("lotId") lotId: Int,
        @Body reserve: Reserve
    )
}