package com.gebeya.parking_lot.data.network.repository

import com.gebeya.parking_lot.data.network.api.LotApi
import com.gebeya.parking_lot.data.network.model.Lot
import com.gebeya.parking_lot.data.network.model.LotResponse
import com.gebeya.parking_lot.data.network.model.Vehicle
import com.gebeya.parking_lot.domain.repository.Response
import retrofit2.HttpException
import java.io.IOException

class LotRepository(
    private val lotApi: LotApi
) {

    suspend fun createLot(token: String, lot: Lot): Response<LotResponse> {
        return try {
            val response = lotApi.createLot(token = "Bearer $token", lot)
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



    suspend fun getLot(token: String): Response<LotResponse> {
        return try{
            println("toke: $token")
            val response = lotApi.getLot("Bearer $token")
            return Response.Success(data = response)
        }catch (e: HttpException){
            println("HTTP :${e}")
            if (e.code() == 400) {
                Response.Fail(errorMessage = "parking lot id not found")
            } else {
                Response.Fail(errorMessage = e.message ?: "")
            }
        }catch (t: Throwable){
            println("Throwable: ${t.message}")
            Response.Fail(errorMessage = t.message ?: "")
        }
    }

    suspend fun getLotById(token: String, id: Int): Response<LotResponse> {
        return try{
            println("toke: $token")
            val response = lotApi.getLotById("Bearer $token", id)
            return Response.Success(data = response)
        }catch (e: HttpException){
            println("HTTP :${e}")
            if (e.code() == 400) {
                Response.Fail(errorMessage = "parking lot id not found")
            } else {
                Response.Fail(errorMessage = e.message ?: "")
            }
        }catch (t: Throwable){
            println("Throwable: ${t.message}")
            Response.Fail(errorMessage = t.message ?: "")
        }
    }

    suspend fun deleteLot(token: String, id: Int): Response<Unit> {
        return try{
            println("Bearer $token")
            val response = lotApi.deleteLot("Bearer $token", id)
            return Response.Success(data = response)
        }catch (e: HttpException){
            println("HTTP :${e}")
            Response.Fail(errorMessage = e.message ?: "")
        }catch (t: Throwable){
            println("Throwable: ${t.message}")
            Response.Fail(errorMessage = t.message ?: "")
        }
    }


    suspend fun updateLot(token: String, id: Int, lot: Lot): Response<LotResponse> {
        return try{
            println("Bearer $token")
            val response = lotApi.updateLot("Bearer $token", id, lot)
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