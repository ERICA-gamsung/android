package com.erica.gamsung.store.domain

enum class StoreType(
    val description: String,
) {
    RESTAURANT("식당"),
    CAFE("카페"),
    ;

    companion object {
        fun from(value: String): StoreType = entries.first { it.description == value }
    }
}
