package com.gebeya.parking_lot.data.place

import com.google.gson.annotations.SerializedName

data class Place(
    @SerializedName("display_name")
    val displayName: String,
    val lat: String,
    val lon: String,
    @SerializedName("place_id")
    val placeId: Long,
    @SerializedName("osm_type")
    val osmType: String,
    @SerializedName("osm_id")
    val osmId: Long,
    val boundingbox: List<String>,
    val type: String,
    val importance: Double,
    val icon: String?
)
