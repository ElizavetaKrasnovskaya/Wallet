package com.bsuir.cryptowallet.di

import android.content.Context
import com.bsuir.cryptowallet.CryptoApp
import com.bsuir.data.repository.AuthRepositoryImpl
import com.bsuir.data.repository.WalletRepositoryImpl
import com.bsuir.data.source.local.SharedPreferencesUtil
import com.bsuir.domain.interactor.AuthInteractor
import com.bsuir.domain.interactor.WalletInteractor
import com.bsuir.domain.repository.AuthRepository
import com.bsuir.domain.repository.WalletRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object Repository {

    @Provides
    fun injectApp(): CryptoApp {
        return CryptoApp.instance
    }

    @Provides
    fun injectSharedPreferences(): SharedPreferencesUtil<String> {
        return SharedPreferencesUtil()
    }

    @Singleton
    @Provides
    fun injectAuthRepository(shared: SharedPreferencesUtil<String>): AuthRepository {
        return AuthRepositoryImpl(shared)
    }

    @Singleton
    @Provides
    fun injectAuthInteractor(repository: AuthRepositoryImpl): AuthInteractor {
        return AuthInteractor(repository)
    }

    @Singleton
    @Provides
    fun injectWalletRepository(shared: SharedPreferencesUtil<String>, @ApplicationContext context:Context): WalletRepository {
        return WalletRepositoryImpl(shared, context)
    }

    @Singleton
    @Provides
    fun injectWalletInteractor(repository: WalletRepository): WalletInteractor {
        return WalletInteractor(repository)
    }
}