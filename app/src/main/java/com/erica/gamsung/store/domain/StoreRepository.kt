package com.erica.gamsung.store.domain

interface StoreRepository {
    suspend fun updateStore(store: Store)
}
