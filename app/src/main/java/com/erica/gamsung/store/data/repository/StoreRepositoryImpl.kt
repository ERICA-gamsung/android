package com.erica.gamsung.store.data.repository

import com.erica.gamsung.store.data.local.StoreDao
import com.erica.gamsung.store.data.local.StoreEntity
import com.erica.gamsung.store.data.remote.StoreApi
import com.erica.gamsung.store.data.remote.UpdateStoreRequest
import com.erica.gamsung.store.domain.Store
import com.erica.gamsung.store.domain.StoreRepository

class StoreRepositoryImpl(
    private val storeDao: StoreDao,
    private val storeApi: StoreApi,
) : StoreRepository {
    override suspend fun updateStore(store: Store) {
        storeApi.updateStore(
            request = UpdateStoreRequest.from(store),
        )
        storeDao.upsert(StoreEntity.from(store))
    }
}
