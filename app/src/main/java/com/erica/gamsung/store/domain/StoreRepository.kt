package com.erica.gamsung.store.domain

import kotlinx.coroutines.flow.Flow

interface StoreRepository {
    suspend fun updateStore(store: Store)

    suspend fun getStore(): Flow<Store?>
}
