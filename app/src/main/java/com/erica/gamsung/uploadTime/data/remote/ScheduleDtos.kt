package com.erica.gamsung.uploadTime.data.remote

data class ApiResponse(
    val success: Boolean,
    val message: String,
)

data class ApiRequest(
    val id: Long,
)
