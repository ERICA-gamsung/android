package com.erica.gamsung.login.data.remote

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface LoginApi {
    @GET("oauth2/get/{uuid}")
    suspend fun getToken(
        @Path("uuid") uuid: String,
    ): TokenResponse

    @GET("member/delete")
    suspend fun deleteMember(
        @Header("Authorization") bearerToken: String,
    )
}

data class TokenResponse(
    val providerId: Long,
    val accessToken: String,
)
