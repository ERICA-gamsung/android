package com.erica.gamsung.store.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.erica.gamsung.store.domain.Store
import com.erica.gamsung.store.domain.StoreType
import java.time.DayOfWeek
import java.time.LocalTime

@Entity(tableName = "stores")
data class StoreEntity(
    val name: String,
    val address: String,
    val phoneNumber: String,
    val type: StoreType,
    val businessDays: Map<DayOfWeek, Boolean>,
    val openTime: LocalTime,
    val closeTime: LocalTime,
    @PrimaryKey
    val id: Long = 0L,
) {
    companion object {
        fun from(store: Store) =
            StoreEntity(
                name = store.name,
                address = store.address,
                phoneNumber = store.phoneNumber,
                type = store.type,
                businessDays = store.businessDays,
                openTime = store.openTime,
                closeTime = store.closeTime,
                id = 0L,
            )
    }
}
