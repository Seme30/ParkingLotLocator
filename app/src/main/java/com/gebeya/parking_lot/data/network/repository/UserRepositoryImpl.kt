package com.gebeya.parking_lot.data.network.repository

import com.gebeya.parking_lot.data.network.api.UserApi
import com.gebeya.parking_lot.data.network.model.AuthenticationToken
import com.gebeya.parking_lot.data.network.model.PhoneRequest
import com.gebeya.parking_lot.data.network.model.PhoneVerify
import com.gebeya.parking_lot.data.network.model.User
import com.gebeya.parking_lot.domain.repository.UserRepository
import com.gebeya.parking_lot.domain.repository.Response
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException

class UserRepositoryImpl(
    val userApi: UserApi
): UserRepository {

    override suspend fun login(username: String, password: String): Response<AuthenticationToken> {
        TODO("Not yet implemented")
    }

    override suspend fun verifyPhone(phoneNo: String, otp: String): Response<ResponseBody> {
        return try {
            val response = userApi.verifyPhone(
                PhoneVerify(
                    phoneNo = phoneNo,
                    otp = otp
                )
            )
            println("net data: $response")
            Response.Success(data = response)
        } catch (e: HttpException) {
            if (e.code() == 400) {
                Response.Fail(errorMessage = "The code you entered is incorrect. Please try again.")
            } else {
                Response.Fail(errorMessage = e.message ?: "")
            }
        } catch (e: IOException) {
            Response.Fail(errorMessage = e.message ?: "")
        } catch (t: Throwable) {
            Response.Fail(errorMessage = t.message ?: "")
        }
    }


    override suspend fun register(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun authUser(phoneNo: PhoneRequest) {
        println("Auth user: $phoneNo")
        return userApi.auth(phoneNo)
    }

    override suspend fun getUser(): Response<User> {
        TODO("Not yet implemented")
    }

    override suspend fun createUser(): Response<User> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(id: Int, user: User) {
        TODO("Not yet implemented")
    }

}