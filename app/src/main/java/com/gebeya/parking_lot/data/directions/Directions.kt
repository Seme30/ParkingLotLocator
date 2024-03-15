package com.gebeya.parking_lot.data.directions

data class DirectionsResponse(
    val type: String,
    val metadata: Metadata,
    val bbox: List<Double>,
    val features: List<Feature>
)

data class Metadata(
    val attribution: String,
    val service: String,
    val timestamp: Long,
    val query: Query,
    val engine: Engine
)

data class Query(
    val coordinates: List<List<Double>>,
    val profile: String,
    val format: String
)

data class Engine(
    val version: String,
    val build_date: String,
    val graph_date: String
)

data class Feature(
    val bbox: List<Double>,
    val type: String,
    val properties: Properties,
    val geometry: Geometry
)

data class Properties(
    val transfers: Int,
    val fare: Int,
    val segments: List<Segment>,
    val way_points: List<Int>,
    val summary: Summary
)

data class Segment(
    val distance: Double,
    val duration: Double,
    val steps: List<Step>
)

data class Step(
    val distance: Double,
    val duration: Double,
    val type: Int,
    val instruction: String,
    val name: String,
    val way_points: List<Int>
)

data class Summary(
    val distance: Double,
    val duration: Double
)

data class Geometry(
    val coordinates: List<List<Double>>,
    val type: String
)
