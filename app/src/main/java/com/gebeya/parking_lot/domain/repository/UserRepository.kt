package com.gebeya.parking_lot.domain.repository

import com.gebeya.parking_lot.data.network.model.AuthenticationToken
import com.gebeya.parking_lot.data.network.model.PhoneRequest
import com.gebeya.parking_lot.data.network.model.User
import okhttp3.ResponseBody

interface UserRepository {

    suspend fun login(username: String, password: String): Response<AuthenticationToken>


    suspend fun verifyPhone(phoneNo: String, otp: String): Response<ResponseBody>

    suspend fun register(user: User)

    suspend fun authUser(phoneNo: PhoneRequest)

    suspend fun getUser(): Response<User>
    suspend fun createUser(): Response<User>

    suspend fun updateUser(user: User)

    suspend fun deleteUser(id: Int, user: User)

}