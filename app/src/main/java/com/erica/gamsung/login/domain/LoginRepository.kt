package com.erica.gamsung.login.domain

interface LoginRepository {
    suspend fun fetchAccessToken(uuid: String): String

    fun getSavedAccessToken(): String?

    fun saveUUID(uuid: String)

    fun getSavedUUID(): String?

    suspend fun hasAccount(): Boolean

    fun clearSession()
}
