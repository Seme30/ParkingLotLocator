package com.gebeya.parking_lot.domain.repository

import com.gebeya.parking_lot.data.network.model.Driver
import com.gebeya.parking_lot.data.network.model.DriverResponse
import com.gebeya.parking_lot.data.network.model.Park
import com.gebeya.parking_lot.data.network.model.Provider
import com.gebeya.parking_lot.data.network.model.ProviderResponse

interface DriverRepository {
    suspend fun createDriver(driver: Driver): Response<DriverResponse>

    suspend fun getDriver(token: String): Response<Driver>
    suspend fun createProvider(provider: Provider): Response<ProviderResponse>

    suspend fun getProvider(token: String): Response<Provider>
    suspend fun getParks(token: String, latitude: Double, longitude: Double): Response<List<Park>>

    suspend fun updateProvider(token: String, id: Int, provider: Provider): Response<Provider>

    suspend fun updateDriver(token: String, id: Int, driver: Driver): Response<Driver>

}