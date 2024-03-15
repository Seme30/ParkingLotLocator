package com.gebeya.parking_lot.data.network.model

data class Vehicle(
    val id: String?,
    val name: String,
    val model: String,
    val year: Int?,
    val plate: String
)
