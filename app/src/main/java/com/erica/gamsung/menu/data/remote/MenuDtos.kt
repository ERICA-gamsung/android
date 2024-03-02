package com.erica.gamsung.menu.data.remote

data class UpdateMenusRequest(
    val name: String,
    val price: Int,
)

data class UpdateMenusResponse(
    val id: Long,
    val name: String,
    val price: Int,
)
