package com.gebeya.parking_lot.data.keystore

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec

class KeyStoreAccessor(
    context: Context
) {

    private val KEY_NAME = "user_role"
    private val SHARED_PREFS_NAME = "RolePrefs"
    private val ROLE_KEY = "role"
    private val IV_KEY = "iv"

    private val keyStore: KeyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    fun getRole(): Role? {
        val encryptedRole = sharedPreferences.getString(ROLE_KEY, null) ?: return null
        val iv = sharedPreferences.getString(IV_KEY, null)?.let { Base64.decode(it, Base64.DEFAULT) } ?: return null
        val secretKeyEntry = keyStore.getEntry(KEY_NAME, null) as? KeyStore.SecretKeyEntry ?: return null
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, secretKeyEntry.secretKey, GCMParameterSpec(128, iv))
        val decodedData = cipher.doFinal(Base64.decode(encryptedRole, Base64.DEFAULT))
        return when (String(decodedData, Charsets.UTF_8)) {
            Role.Driver.roleString -> Role.Driver
            Role.Provider.roleString -> Role.Provider
            else -> null
        }
    }

    fun setRole(role: Role) {
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            "AndroidKeyStore" // Use AndroidKeyStore
        )
        keyGenerator.init(
            KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setUserAuthenticationRequired(false)
                .build()
        )
        val secretKey = keyGenerator.generateKey()
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encodedData = cipher.doFinal(role.roleString.toByteArray(Charsets.UTF_8))
        sharedPreferences.edit().apply {
            putString(ROLE_KEY, Base64.encodeToString(encodedData, Base64.DEFAULT))
            putString(IV_KEY, Base64.encodeToString(cipher.iv, Base64.DEFAULT))
        }.apply()
    }

    fun deleteRole(){
        sharedPreferences.edit().apply {
            remove(ROLE_KEY)
            remove(IV_KEY)
        }.apply()
    }
}






