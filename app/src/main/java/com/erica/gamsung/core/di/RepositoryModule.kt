package com.erica.gamsung.core.di

import com.erica.gamsung.menu.data.local.MenuDao
import com.erica.gamsung.menu.data.remote.MenuApi
import com.erica.gamsung.menu.data.repository.MenuRepositoryImpl
import com.erica.gamsung.menu.domain.MenuRepository
import com.erica.gamsung.store.data.local.StoreDao
import com.erica.gamsung.store.data.remote.StoreApi
import com.erica.gamsung.store.data.repository.StoreRepositoryImpl
import com.erica.gamsung.store.domain.StoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideMenuRepository(
        menuDao: MenuDao,
        menuApi: MenuApi,
    ): MenuRepository = MenuRepositoryImpl(menuDao = menuDao, menuApi = menuApi)

    @Singleton
    @Provides
    fun provideStoreRepository(
        storeDao: StoreDao,
        storeApi: StoreApi,
    ): StoreRepository = StoreRepositoryImpl(storeDao = storeDao, storeApi = storeApi)
}
