package com.erica.gamsung.login.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.erica.gamsung.login.data.remote.LoginApi
import com.erica.gamsung.login.data.remote.TokenResponse
import com.erica.gamsung.login.domain.LoginRepository
import com.erica.gamsung.menu.data.remote.MenuApi
import com.erica.gamsung.store.data.remote.StoreApi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class LoginRepositoryImpl(
    private val loginApi: LoginApi,
    private val menuApi: MenuApi,
    private val storeApi: StoreApi,
    @ApplicationContext private val context: Context,
) : LoginRepository {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    override suspend fun fetchAccessToken(uuid: String): String {
        val tokenResponse = loginApi.getToken(uuid)
        saveToken(tokenResponse)
        return tokenResponse.accessToken
    }

    override fun getSavedAccessToken(): String? = sharedPreferences.getString("access_token", null)

    override fun saveUUID(uuid: String) {
        sharedPreferences.edit().putString("uuid", uuid).apply()
    }

    override fun getSavedUUID(): String? = sharedPreferences.getString("uuid", null)

    override suspend fun hasAccount(): Boolean =
        coroutineScope {
            val menuResult = async { runCatching { menuApi.getMenus() }.isSuccess }
            val storeResult = async { runCatching { storeApi.getStore() }.isSuccess }

            menuResult.await() && storeResult.await()
        }

    private fun saveToken(token: TokenResponse) {
        sharedPreferences.edit().putString("access_token", token.accessToken).apply()
        sharedPreferences.edit().putLong("memberId", token.id).apply()
    }

    override fun clearSession() {
        sharedPreferences.edit().clear().apply()
    }

    override suspend fun withDraw() {
        getSavedAccessToken()?.let { token ->
            val bearerToken = "Bearer $token"
            loginApi.deleteMember(bearerToken)
        }
    }

    override fun getMemberId(): Long = sharedPreferences.getLong("memberId", 1L)
}
