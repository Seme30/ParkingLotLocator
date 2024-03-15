package com.gebeya.parking_lot.data.network.repository

import com.gebeya.parking_lot.data.network.api.OpApi
import com.gebeya.parking_lot.data.network.model.Lot
import com.gebeya.parking_lot.data.network.model.LotResponse
import com.gebeya.parking_lot.data.network.model.TimeItem
import com.gebeya.parking_lot.data.network.model.TimeItemResponse
import com.gebeya.parking_lot.domain.repository.Response
import retrofit2.HttpException
import java.io.IOException


class OpRepository(
    val opApi: OpApi
) {

    suspend fun createOpTime(token: String, lotId: Int, timeItems: List<TimeItem>): Response<List<TimeItemResponse>> {
        return try {
            val response = opApi.createOpTimes(
                token = "Bearer $token",
                id = lotId,
                timeItems)

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



    suspend fun getOpTimes(
        token: String,
        lotId: Int,
        ): Response<List<TimeItemResponse>> {
        return try{
            println("toke: $token")
            val response = opApi.getOpTimes("Bearer $token", id = lotId)
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

    suspend fun deleteOpTime(token: String, lotId: Int, opId: Int): Response<Unit> {
        return try{
            println("Bearer $token")
            val response = opApi.deleteOpTime(
                "Bearer $token",
                lotId = lotId,
                operationId = opId
                )
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