package com.gebeya.parking_lot.domain.repository

import com.gebeya.parking_lot.data.network.model.Vehicle

interface VehicleRepository {

    suspend fun createVehicle(token: String, vehicle: Vehicle): Response<Vehicle>

    suspend fun getVehicles(token: String): Response<List<Vehicle>>

    suspend fun deleteVehicle(token: String, id: Int): Response<Unit>

    suspend fun getVehicleById(token: String, id: Int): Response<Vehicle>

    suspend fun updateVehicle(token: String, id: Int, vehicle: Vehicle): Response<Vehicle>

}