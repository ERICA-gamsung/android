package com.erica.gamsung.menu.data.remote

import com.erica.gamsung.core.di.SingletonModule
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Query

interface MenuApi {
    @PUT("menu/put/{userId}")
    suspend fun updateMenus(
        @Query("userId") userId: Long = 1L,
        @Body request: List<UpdateMenusRequest>,
    ): List<UpdateMenusResponse>

    companion object {
        val service: MenuApi = SingletonModule.retrofit.create(MenuApi::class.java)
    }
}
