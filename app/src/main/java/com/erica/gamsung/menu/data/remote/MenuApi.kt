package com.erica.gamsung.menu.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface MenuApi {
    @PUT("menu/put/{userId}")
    suspend fun updateMenus(
        @Path("userId") userId: Long = 1L,
        @Body request: List<UpdateMenusRequest>,
    ): List<UpdateMenusResponse>

    @GET("menu/get/{userId}")
    suspend fun getMenus(
        @Path("userId") userId: Long = 1L,
    ): List<GetMenusResponse>
}
