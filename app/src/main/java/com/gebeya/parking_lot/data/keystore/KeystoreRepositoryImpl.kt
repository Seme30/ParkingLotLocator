package com.gebeya.parking_lot.data.keystore

import com.gebeya.parking_lot.domain.repository.KeystoreRepository

class KeystoreRepositoryImpl(
    private val keyStoreAccessor: KeyStoreAccessor
): KeystoreRepository {

    override suspend fun getRole(): Role? {
        return keyStoreAccessor.getRole()
    }

    override suspend fun setRole(role: Role) {
        return keyStoreAccessor.setRole(role)
    }

    override suspend fun deleteRole() {
        keyStoreAccessor.deleteRole()
    }
}