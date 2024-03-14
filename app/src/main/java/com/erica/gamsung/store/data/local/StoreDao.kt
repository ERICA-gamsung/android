package com.erica.gamsung.store.data.local

import androidx.room.Dao
import androidx.room.Upsert

@Dao
interface StoreDao {
    @Upsert
    fun upsert(store: StoreEntity)
}
