package com.gebeya.parking_lot.data.network.model



data class Provider(
    val id: Int?,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNo: String,
    val imageUrl: String?,
)

data class ProviderResponse (
    val token: String
)


//data class coordinate(
//    val longitude: Double,
//    val latitude: Double
//)
