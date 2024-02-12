package com.gebeya.parking_lot.data.network.repository

import com.gebeya.parking_lot.data.network.api.UserApi
import com.gebeya.parking_lot.data.network.model.AuthenticationToken
import com.gebeya.parking_lot.data.network.model.PhoneVerify
import com.gebeya.parking_lot.data.network.model.User
import com.gebeya.parking_lot.domain.repository.UserRepository
import com.training.gebeya.safari_wfp.domain.repository.Response
import okhttp3.ResponseBody

class UserRepositoryImpl(
    val userApi: UserApi
): UserRepository {

    override suspend fun login(username: String, password: String): Response<AuthenticationToken> {
        TODO("Not yet implemented")
    }

    override suspend fun verifyPhone(phoneNo: String, otp: String): ResponseBody {


        println("inside repository $phoneNo opt: $otp")
//        val params = HashMap<String, String>()
//        params["phoneNo"] = phoneNo
//        params["otp"] = otp
        return userApi.verifyPhone(
            PhoneVerify(
                phoneNo = phoneNo,
                otp = otp
            )
        )
    }

    override suspend fun register(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun authUser(phone: String) {
        return userApi.auth(phone)
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