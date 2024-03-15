package com.gebeya.parking_lot.data.network.api

import com.gebeya.parking_lot.data.network.model.TimeItem
import com.gebeya.parking_lot.data.network.model.TimeItemResponse
import com.gebeya.parking_lot.data.network.model.Vehicle
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface OpApi {
    @GET("parking-lot/lots/{id}/operation-hours")
    suspend fun getOpTimes(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): List<TimeItemResponse>


    @POST("parking-lot/lots/{id}/operation-hours")
    suspend fun createOpTimes(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body timeItems: List<TimeItem>
    ): List<TimeItemResponse>


    @DELETE("parking-lot/lots/{lotId}/operation-hours/{operationId}")
    suspend fun deleteOpTime(
        @Header("Authorization") token: String,
        @Path("lotId") lotId: Int,
        @Path("operationId") operationId: Int
    )

}