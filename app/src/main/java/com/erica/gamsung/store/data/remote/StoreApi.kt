package com.erica.gamsung.store.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface StoreApi {
    @PUT("storeInfos/{userId}")
    suspend fun updateStore(
        @Path("userId") userId: Long = 1L,
        @Body request: UpdateStoreRequest,
    )

    @GET("storeInfos/{userId}")
    suspend fun getStore(
        @Path("userId") userId: Long = 1L,
    ): GetStoreResponse
}
