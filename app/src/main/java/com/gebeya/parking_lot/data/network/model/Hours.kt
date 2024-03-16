package com.gebeya.parking_lot.data.network.model

import java.time.LocalTime

//data class Time(
//    val hour: Int,
//    val minute: Int,
//    val second: Int,
//    val nano: Int
//)

data class TimeItem(
    val id: Int? = null,
    val startTime: String,
    val endTime: String,
    val price: Double
)


data class TimeItemResponse(
    val id: Int? = null,
    val startTime: String,
    val endTime: String,
    val price: Double
)

data class Reserve(
    val stayingDuration: String,
    val vehicleId: Int
)