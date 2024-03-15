package com.gebeya.parking_lot.data.network.model

data class Driver(
    val id: Int?,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNo: String,
    val imageUrl: String?
)

data class DriverResponse (
   val token: String
)


data class Park (
    val id: Int? = null,
    val title: String,
    val description: String,
    val distance: Double,
    val coordinate: Coordinate
)

data class Coordinate (
    val latitude: Double,
    val longitude: Double,
)
