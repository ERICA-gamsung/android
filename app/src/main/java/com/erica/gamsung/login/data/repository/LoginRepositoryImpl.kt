package com.erica.gamsung.login.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.erica.gamsung.login.data.remote.LoginApi
import com.erica.gamsung.login.domain.LoginRepository
import dagger.hilt.android.qualifiers.ApplicationContext

class LoginRepositoryImpl(
    private val loginApi: LoginApi,
    @ApplicationContext private val context: Context,
) : LoginRepository {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    override suspend fun fetchAccessToken(uuid: String): String {
        val accessToken = loginApi.getToken(uuid).accessToken
        saveToken(accessToken)
        return accessToken
    }

    override fun getSavedAccessToken(): String? = sharedPreferences.getString("access_token", null)

    override fun saveUUID(uuid: String) {
        sharedPreferences.edit().putString("uuid", uuid).apply()
    }

    override fun getSavedUUID(): String? = sharedPreferences.getString("uuid", null)

    private fun saveToken(token: String) {
        sharedPreferences.edit().putString("access_token", token).apply()
    }
}
