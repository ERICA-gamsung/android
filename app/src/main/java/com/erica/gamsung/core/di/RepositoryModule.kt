package com.erica.gamsung.core.di

import android.content.Context
import com.erica.gamsung.login.data.remote.LoginApi
import com.erica.gamsung.login.data.repository.LoginRepositoryImpl
import com.erica.gamsung.login.domain.LoginRepository
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
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Singleton
    @Provides
    fun provideLoginRepository(
        loginApi: LoginApi,
        menuApi: MenuApi,
        storeApi: StoreApi,
        @ApplicationContext context: Context,
    ): LoginRepository =
        LoginRepositoryImpl(
            loginApi = loginApi,
            menuApi = menuApi,
            storeApi = storeApi,
            context = context,
        )
}
