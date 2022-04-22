package com.bsuir.data.repository

import android.app.Application
import android.content.Context
import com.bsuir.data.const.KEY_MNEMONIC
import com.bsuir.data.source.local.SharedPreferencesUtil
import com.bsuir.domain.repository.WalletRepository
import io.horizontalsystems.bitcoincore.BitcoinCore
import io.horizontalsystems.bitcoincore.core.Bip
import io.horizontalsystems.bitcoinkit.BitcoinKit
import wallet.core.jni.HDWallet
import javax.inject.Inject

class WalletRepositoryImpl @Inject constructor(
    private val shared: SharedPreferencesUtil<String>,
private val context: Context
    ) : WalletRepository {

    private val walletId = "MyWallet"
    private val networkType = BitcoinKit.NetworkType.TestNet
    private val syncMode = BitcoinCore.SyncMode.Api()
    private val bip = Bip.BIP44
    private lateinit var bitcoinKit: BitcoinKit

    override fun createWallet(context: Application): HDWallet {
        val wallet = HDWallet(128, "")
        shared.saveData(context, KEY_MNEMONIC, wallet.mnemonic().toString())
//        val words = wallet.mnemonic().toString().split(" ")
//        val passphrase = ""
//        bitcoinKit = BitcoinKit(
//            context,
//            words,
//            passphrase,
//            walletId,
//            networkType,
//            syncMode = syncMode,
//            bip = bip
//        )
//        bitcoinKit.start()
        return wallet
    }

    override fun importWallet(mnemonic: String, context: Application): HDWallet {
        val wallet = HDWallet(mnemonic, "")
        shared.saveData(context, KEY_MNEMONIC, wallet.mnemonic().toString())
//        val words = mnemonic.split(" ")
//        val passphrase = ""
//        bitcoinKit = BitcoinKit(
//            context,
//            words,
//            passphrase,
//            walletId,
//            networkType,
//            syncMode = syncMode,
//            bip = bip
//        )
//        bitcoinKit.start()
        return wallet
    }

    override fun getBitcoinKit(): BitcoinKit {
        return bitcoinKit
    }
}