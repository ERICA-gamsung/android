package com.erica.gamsung.store.domain

import kotlinx.coroutines.flow.Flow

interface StoreRepository {
    suspend fun updateStore(
        store: Store,
        memberId: Long,
    )

    suspend fun getStore(memberId: Long): Flow<Store?>
}
