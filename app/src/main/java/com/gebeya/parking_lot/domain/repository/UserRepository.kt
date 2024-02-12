package com.gebeya.parking_lot.domain.repository

import com.gebeya.parking_lot.data.network.model.AuthenticationToken
import com.gebeya.parking_lot.data.network.model.User
import com.training.gebeya.safari_wfp.domain.repository.Response
import okhttp3.ResponseBody

interface UserRepository {

    suspend fun login(username: String, password: String): Response<AuthenticationToken>


    suspend fun verifyPhone(phoneNo: String, otp: String): ResponseBody

    suspend fun register(user: User)

    suspend fun authUser(phone: String)

    suspend fun getUser(): Response<User>
    suspend fun createUser(): Response<User>

    suspend fun updateUser(user: User)

    suspend fun deleteUser(id: Int, user: User)

}