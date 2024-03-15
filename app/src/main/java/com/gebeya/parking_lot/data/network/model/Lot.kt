package com.gebeya.parking_lot.data.network.model

data class Lot(
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val capacity: Int,
    val parkingType: String,
    val imageUrl: List<String>,
)




data class LotResponse(
    val id: Int?,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val capacity: Int,
    val images: List<String>,
    val availableSlot: Int,
    val parkingType: String,
    val rating: Int
)