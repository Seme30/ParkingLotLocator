package com.gebeya.parking_lot.data.directions

class DirectionsRepository(
    val directionsApi: DirectionsApi
) {

    suspend fun getDirections(lonlat1: String, lonlat2: String): DirectionsResponse {
        val apikey = "5b3ce3597851110001cf62485392d85e32ae4584ad950b93acd6c38d"
        return directionsApi.getDirections(
            apikey,
            lonlat1,
            lonlat2
        )
    }

}