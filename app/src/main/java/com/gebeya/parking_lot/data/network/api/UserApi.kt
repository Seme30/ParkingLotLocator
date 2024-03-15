package com.gebeya.parking_lot.data.network.api

import com.gebeya.parking_lot.data.network.model.AuthenticationToken
import com.gebeya.parking_lot.data.network.model.PhoneRequest
import com.gebeya.parking_lot.data.network.model.PhoneVerify
import com.gebeya.parking_lot.data.network.model.User
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.GET

interface UserApi {

    @GET()
    suspend fun getUser(id: Int): User


    @POST("")
    suspend fun register(user: User)


    @POST("authentication_token")
    suspend fun login(
        @Header("Authorization") authorization: String
    ): AuthenticationToken


    @POST("auth/login")
    suspend fun auth(
        @Body phoneNo: PhoneRequest
    )

    @POST("auth/otp")
    suspend fun verifyPhone(
        @Body params: PhoneVerify
    ): ResponseBody


}