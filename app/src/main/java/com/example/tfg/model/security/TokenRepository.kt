package com.example.tfg.model.security

import javax.inject.Inject

class TokenRepository @Inject constructor(
    private val secureTokenStore: SecureTokenStore
) {
    suspend fun saveTokens(accessToken: String, refreshToken: String) {
        secureTokenStore.save("access_token", accessToken)
        secureTokenStore.save("refresh_token", refreshToken)
    }

    suspend fun getAccessToken(): String? = secureTokenStore.read("access_token")

    suspend fun getRefreshToken(): String? = secureTokenStore.read("refresh_token")

    suspend fun clearTokens() = secureTokenStore.clearAll()
}