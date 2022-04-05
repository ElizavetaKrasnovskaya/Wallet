package com.bsuir.data.repository

import com.bsuir.domain.repository.WalletRepository
import wallet.core.jni.HDWallet

class WalletRepositoryImpl: WalletRepository {

    override fun createWallet(): HDWallet {
        return HDWallet(128, "")
    }

    override fun importWallet(mnemonic: String): HDWallet {
        return HDWallet(mnemonic, "")
    }
}