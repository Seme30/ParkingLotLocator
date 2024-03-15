package com.gebeya.parking_lot.data.network.model

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val phoneNo: String?,
//    val image: String?,
)

data class AuthenticationToken(
    val token: String,
    @SerializedName("expires_at")
    val expiresAt: String,
)

data class PhoneRequest(
    val phoneNo: String
)

data class PhoneVerify(
    val phoneNo: String,
    val otp: String
)

data class PhoneVerifyResponse(
    @SerializedName("token") val token: String,
    @SerializedName("code") val code: String
)
