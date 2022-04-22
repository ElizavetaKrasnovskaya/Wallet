package com.bsuir.domain.interactor

import android.app.Application
import com.bsuir.domain.repository.WalletRepository
import wallet.core.jni.HDWallet
import javax.inject.Inject

class WalletInteractor @Inject constructor(private val repository: WalletRepository) {

    fun createWallet(context: Application): HDWallet {
        return repository.createWallet(context)
    }

    fun importWallet(mnemonic: String, context: Application): HDWallet {
        return repository.importWallet(mnemonic, context)
    }

    fun getBitcoinKit() = repository.getBitcoinKit()
}