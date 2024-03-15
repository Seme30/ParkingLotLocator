package com.gebeya.parking_lot.data.place

class PlaceRepository(private val apiService: PlaceApi) {

    suspend fun searchPlaces(query: String): List<Place> {
        return apiService.searchPlaces(query = query)
    }
}
