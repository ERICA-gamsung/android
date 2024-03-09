package com.erica.gamsung.core.di

import com.erica.gamsung.menu.data.local.MenuDao
import com.erica.gamsung.menu.data.remote.MenuApi
import com.erica.gamsung.menu.data.repository.MenuRepositoryImpl
import com.erica.gamsung.menu.domain.MenuRepository
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
}
