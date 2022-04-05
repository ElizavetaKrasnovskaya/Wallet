package com.bsuir.domain.repository

import wallet.core.jni.HDWallet

interface WalletRepository {

    fun createWallet(): HDWallet

    fun importWallet(mnemonic: String): HDWallet
}