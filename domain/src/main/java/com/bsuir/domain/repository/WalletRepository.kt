package com.bsuir.domain.repository

import android.app.Application
import io.horizontalsystems.bitcoinkit.BitcoinKit
import wallet.core.jni.HDWallet

interface WalletRepository {

    fun createWallet(context: Application): HDWallet

    fun importWallet(mnemonic: String, context: Application): HDWallet

    fun getBitcoinKit(): BitcoinKit
}