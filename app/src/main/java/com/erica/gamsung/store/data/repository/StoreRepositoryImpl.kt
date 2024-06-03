package com.erica.gamsung.store.data.repository

import com.erica.gamsung.store.data.local.StoreDao
import com.erica.gamsung.store.data.local.StoreEntity
import com.erica.gamsung.store.data.remote.StoreApi
import com.erica.gamsung.store.data.remote.UpdateStoreRequest
import com.erica.gamsung.store.domain.Store
import com.erica.gamsung.store.domain.StoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StoreRepositoryImpl(
    private val storeDao: StoreDao,
    private val storeApi: StoreApi,
) : StoreRepository {
    override suspend fun updateStore(
        store: Store,
        memberId: Long,
    ) {
        storeApi.updateStore(
            request = UpdateStoreRequest.from(store),
            userId = memberId,
        )
        storeDao.upsert(StoreEntity.from(store))
    }

    override suspend fun getStore(memberId: Long): Flow<Store?> =
        flow {
            // 1. 로컬 DB에 있는 데이터 반환
            val localData: Store? = storeDao.getStoreById(0L)?.toDomainModel()
            emit(localData)

            // 2. 서버에서 받은 데이터 동기화 및 반환
            runCatching {
                storeApi.getStore(memberId).toDomainModel()
            }.onSuccess { remoteData ->
                storeDao.upsert(StoreEntity.from(remoteData))
                emit(remoteData)
            }
        }
}
