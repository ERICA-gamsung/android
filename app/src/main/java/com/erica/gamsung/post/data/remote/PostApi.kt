package com.erica.gamsung.post.data.remote

import com.erica.gamsung.post.domain.Schedule
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApi {
    @GET("api/v1/postings/{reservationId}")
    suspend fun fetchPostData(
        @Path("reservatioId") reservationId: Int,
    ): Response<Schedule>
}
