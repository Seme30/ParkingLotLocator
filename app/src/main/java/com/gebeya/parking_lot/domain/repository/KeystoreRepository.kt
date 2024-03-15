package com.gebeya.parking_lot.domain.repository

import com.gebeya.parking_lot.data.keystore.Role

interface KeystoreRepository {

    suspend fun getRole(): Role?

    suspend fun setRole(role: Role)

    suspend fun deleteRole()

}