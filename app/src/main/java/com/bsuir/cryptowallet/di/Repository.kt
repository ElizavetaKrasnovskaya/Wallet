package com.bsuir.cryptowallet.di

import com.bsuir.cryptowallet.CryptoApp
import com.bsuir.data.repository.AuthRepositoryImpl
import com.bsuir.data.source.local.SharedPreferencesDataSource
import com.bsuir.domain.interactor.AuthInteractor
import com.bsuir.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object Repository {

    @Singleton
    @Provides
    fun injectApp(): CryptoApp {
        return CryptoApp()
    }

    @Singleton
    @Provides
    fun injectSharedPreferences(app: CryptoApp): SharedPreferencesDataSource {
        return SharedPreferencesDataSource(app)
    }

    @Singleton
    @Provides
    fun injectAuthRepository(shared: SharedPreferencesDataSource): AuthRepository {
        return AuthRepositoryImpl(shared)
    }

    @Singleton
    @Provides
    fun injectInteractor(repository: AuthRepositoryImpl): AuthInteractor {
        return AuthInteractor(repository)
    }

}