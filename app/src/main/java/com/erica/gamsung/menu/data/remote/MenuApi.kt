package com.erica.gamsung.menu.data.remote

import com.erica.gamsung.core.di.NetworkModule
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface MenuApi {
    @PUT("menu/put/{userId}")
    suspend fun updateMenus(
        @Path("userId") userId: Long = 1L,
        @Body request: List<UpdateMenusRequest>,
    ): List<UpdateMenusResponse>

    companion object {
        val service: MenuApi = NetworkModule.retrofit.create(MenuApi::class.java)
    }
}
