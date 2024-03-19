package com.erica.gamsung.menu.data.remote

import com.erica.gamsung.menu.domain.Menu

data class UpdateMenusRequest(
    val name: String,
    val price: Int,
)

data class UpdateMenusResponse(
    val id: Long,
    val name: String,
    val price: Int,
)

data class GetMenusResponse(
    val id: Long,
    val name: String,
    val price: Int,
) {
    fun toDomainModel(): Menu =
        Menu(
            name = name,
            price = price,
        )
}
