package com.gebeya.parking_lot.data.network.repository

import com.gebeya.parking_lot.data.network.api.DriverApi
import com.gebeya.parking_lot.data.network.model.Driver
import com.gebeya.parking_lot.data.network.model.DriverResponse
import com.gebeya.parking_lot.data.network.model.Park
import com.gebeya.parking_lot.data.network.model.Provider
import com.gebeya.parking_lot.data.network.model.ProviderResponse
import com.gebeya.parking_lot.domain.repository.DriverRepository
import com.gebeya.parking_lot.domain.repository.Response
import retrofit2.HttpException
import java.io.IOException


class DriverRepositoryImpl(
    val driverApi: DriverApi
) : DriverRepository{


    override suspend fun createDriver(driver: Driver): Response<DriverResponse> {
        return try {
            val response = driverApi.createDriver(driver)
            println("net data: $response")
            Response.Success(data = response)
        }catch (e: HttpException){
            Response.Fail(errorMessage = e.message ?: "")
        } catch (e: IOException){
            Response.Fail(errorMessage = e.message?: "")
        }catch (t: Throwable){
            Response.Fail(errorMessage = t.message ?: "")
        }
    }
    override suspend fun createProvider(provider: Provider): Response<ProviderResponse> {
        return try {
            val response = driverApi.createProvider(provider)
            println("net data: $response")
            Response.Success(data = response)
        }catch (e: HttpException){
            Response.Fail(errorMessage = e.message ?: "")
        } catch (e: IOException){
            Response.Fail(errorMessage = e.message?: "")
        }catch (t: Throwable){
            Response.Fail(errorMessage = t.message ?: "")
        }
    }


    override suspend fun getDriver(token: String): Response<Driver> {
        return try {
            val response = driverApi.getDriver("Bearer $token")
            println("net data: $response")
            Response.Success(data = response)
        }catch (e: HttpException){
            Response.Fail(errorMessage = e.message ?: "")
        } catch (e: IOException){
            Response.Fail(errorMessage = e.message?: "")
        }catch (t: Throwable){
            Response.Fail(errorMessage = t.message ?: "")
        }
    }

    override suspend fun getProvider(token: String): Response<Provider> {
        return try {
            val response = driverApi.getProvider("Bearer $token")
            println("net data: $response")
            Response.Success(data = response)
        }catch (e: HttpException){
            Response.Fail(errorMessage = e.message ?: "")
        } catch (e: IOException){
            Response.Fail(errorMessage = e.message?: "")
        }catch (t: Throwable){
            Response.Fail(errorMessage = t.message ?: "")
        }
    }

    override suspend fun getParks(token: String, latitude: Double, longitude: Double): Response<List<Park>> {
        return try {
            val response = driverApi.getParks("Bearer $token",latitude = latitude, longitude = longitude)
            println("net data: $response")
            Response.Success(data = response)
        }catch (e: HttpException){
            Response.Fail(errorMessage = e.message ?: "")
        } catch (e: IOException){
            Response.Fail(errorMessage = e.message?: "")
        }catch (t: Throwable){
            Response.Fail(errorMessage = t.message ?: "")
        }
    }

    override suspend fun updateProvider(
        token: String,
        id: Int,
        provider: Provider
    ): Response<Provider> {
        return try{
            println("Bearer $token")
            val response = driverApi.updateProvider("Bearer $token", id, provider)
            return Response.Success(data = response)
        }catch (e: HttpException){
            println("HTTP :${e}")
            Response.Fail(errorMessage = e.message ?: "")
        }catch (e: IOException){
            Response.Fail(errorMessage = e.message?: "")
        } catch (t: Throwable){
            println("Throwable: ${t.message}")
            Response.Fail(errorMessage = t.message ?: "")
        }
    }

    override suspend fun updateDriver(token: String, id: Int, driver: Driver): Response<Driver> {

        return try{
            println("Bearer $token")
            val response = driverApi.updateDriver("Bearer $token", id, driver)
            return Response.Success(data = response)
        }catch (e: HttpException){
            println("HTTP :${e}")
            Response.Fail(errorMessage = e.message ?: "")
        }catch (e: IOException){
            Response.Fail(errorMessage = e.message?: "")
        } catch (t: Throwable){
            println("Throwable: ${t.message}")
            Response.Fail(errorMessage = t.message ?: "")
        }

    }
}