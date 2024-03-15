package com.gebeya.parking_lot.data.place

import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceApi{
    @GET("/search")
    suspend fun searchPlaces(@Query("format") format: String = "json", @Query("q") query: String): List<Place>
}
