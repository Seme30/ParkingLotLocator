package com.gebeya.parking_lot.data.network.repository

import com.gebeya.parking_lot.data.network.api.VehicleApi
import com.gebeya.parking_lot.data.network.model.Vehicle
import com.gebeya.parking_lot.domain.repository.Response
import com.gebeya.parking_lot.domain.repository.VehicleRepository
import retrofit2.HttpException

class VehicleRepositoryImpl(
    private val vehicleApi: VehicleApi
): VehicleRepository{
    override suspend fun createVehicle(token: String, vehicle: Vehicle): Response<Vehicle> {
        return try{
            println("Bearer $token")
            val response = vehicleApi.createVehicle("Bearer $token", vehicle)
            return Response.Success(data = response)
        }catch (e: HttpException){
            println("HTTP :${e}")
            Response.Fail(errorMessage = e.message ?: "")
        }catch (t: Throwable){
            println("Throwable: ${t.message}")
            Response.Fail(errorMessage = t.message ?: "")
        }
    }

    override suspend fun getVehicles(token: String): Response<List<Vehicle>> {
        return try{
            println("toke: $token")
            val response = vehicleApi.getVehicles("Bearer $token")
            return Response.Success(data = response)
        }catch (e: HttpException){
            println("HTTP :${e}")
            Response.Fail(errorMessage = e.message ?: "")
        }catch (t: Throwable){
            println("Throwable: ${t.message}")
            Response.Fail(errorMessage = t.message ?: "")
        }
    }

    override suspend fun deleteVehicle(token: String, id: Int): Response<Unit> {
        return try{
            println("Bearer $token")
            val response = vehicleApi.deleteVehicle("Bearer $token", id)
            return Response.Success(data = response)
        }catch (e: HttpException){
            println("HTTP :${e}")
            Response.Fail(errorMessage = e.message ?: "")
        }catch (t: Throwable){
            println("Throwable: ${t.message}")
            Response.Fail(errorMessage = t.message ?: "")
        }
    }

    override suspend fun getVehicleById(token: String, id: Int): Response<Vehicle> {
        return try{
            println("Bearer $token")
            val response = vehicleApi.getVehicleById("Bearer $token", id)
            return Response.Success(data = response)
        }catch (e: HttpException){
            println("HTTP :${e}")
            Response.Fail(errorMessage = e.message ?: "")
        }catch (t: Throwable){
            println("Throwable: ${t.message}")
            Response.Fail(errorMessage = t.message ?: "")
        }
    }

    override suspend fun updateVehicle(token: String, id: Int, vehicle: Vehicle): Response<Vehicle> {
        return try{
            println("Bearer $token")
            val response = vehicleApi.updateVehicle("Bearer $token", id, vehicle)
            return Response.Success(data = response)
        }catch (e: HttpException){
            println("HTTP :${e}")
            Response.Fail(errorMessage = e.message ?: "")
        }catch (t: Throwable){
            println("Throwable: ${t.message}")
            Response.Fail(errorMessage = t.message ?: "")
        }
    }

}