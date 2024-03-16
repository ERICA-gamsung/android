package com.erica.gamsung.store.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface StoreDao {
    @Upsert
    fun upsert(store: StoreEntity)

    @Query("SELECT * FROM stores WHERE id == :storeId")
    fun getStoreById(storeId: Long): StoreEntity?
}
