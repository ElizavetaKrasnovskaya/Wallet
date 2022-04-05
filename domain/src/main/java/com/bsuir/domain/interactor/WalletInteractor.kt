package com.bsuir.domain.interactor

import com.bsuir.domain.repository.WalletRepository
import wallet.core.jni.HDWallet
import javax.inject.Inject

class WalletInteractor @Inject constructor(private val repository: WalletRepository) {

    fun createWallet(): HDWallet{
        return repository.createWallet()
    }

    fun importWallet(mnemonic: String): HDWallet{
        return repository.importWallet(mnemonic)
    }
}