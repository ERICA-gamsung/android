package com.erica.gamsung.post.data.remote

import com.erica.gamsung.post.data.mock.Post
import com.erica.gamsung.post.domain.Schedule
import com.erica.gamsung.post.domain.ScheduleState
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface PostApi {
    @GET("postings/{reservationId}")
    suspend fun fetchPostData(
        @Path("reservationId") reservationId: Int,
    ): Response<Schedule>

    @GET("postings/{id}/state")
    suspend fun fetchPostListData(
        @Path("id") memberId: Long,
    ): Response<List<ScheduleState>>

    @POST("postings/post/{reservationId}")
    suspend fun confirmPostData(
        @Path("reservationId") reservationId: Int?,
        @Body request: Post,
    )

    @Multipart
    @POST("image/post/{reservationId}")
    suspend fun confirmImgData(
        @Path("reservationId") reservationId: Int?,
        @Part files: List<MultipartBody.Part>,
    ): Response<List<String>>
}
