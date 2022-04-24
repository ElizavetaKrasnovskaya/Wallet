package com.cometchat.pro.uikit.ui_components.shared.cometchatComposeBox

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bsuir.data.const.KEY_MNEMONIC
import com.bsuir.data.source.local.SharedPreferencesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.horizontalsystems.bitcoincore.BitcoinCore
import io.horizontalsystems.bitcoincore.core.Bip
import io.horizontalsystems.bitcoincore.models.BalanceInfo
import io.horizontalsystems.bitcoincore.models.TransactionDataSortType
import io.horizontalsystems.bitcoincore.models.TransactionFilterType
import io.horizontalsystems.bitcoincore.models.TransactionInfo
import io.horizontalsystems.bitcoinkit.BitcoinKit
import io.horizontalsystems.hdwalletkit.HDWallet
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class BalanceViewModel @Inject constructor(
    private val shared: SharedPreferencesUtil<String>,
    @ApplicationContext private val context: Context
) : ViewModel(), BitcoinKit.Listener {

    val wallet = MutableLiveData<HDWallet>()
    val balance = MutableLiveData<BalanceInfo>()
    val isOperationCompleted = MutableLiveData<Boolean>()
    val receivedTransaction = MutableLiveData<TransactionInfo>()
    val sendTransaction = MutableLiveData<TransactionInfo>()
    val receivedAddress = MutableLiveData<String>()

    private val walletId = "MyWallet"
    private val networkType = BitcoinKit.NetworkType.TestNet
    private val syncMode = BitcoinCore.SyncMode.Api()
    private val bip = Bip.BIP44
    private val passphrase = ""
    private lateinit var bitcoinKit: BitcoinKit
    private val disposables = CompositeDisposable()
    private var transactionFilterType: TransactionFilterType? = null

    fun start(context: Context) {
        bitcoinKit = BitcoinKit(
            context,
            shared.fetchData(context, KEY_MNEMONIC, "").split(" "),
            passphrase,
            walletId,
            networkType,
            syncMode = syncMode,
            bip = bip
        )
        bitcoinKit.listener = this
        bitcoinKit.start()
        bitcoinKit.balance
    }

    fun sendBTC(address: String, amount: Long, feeRate: Int) {
        try {
            bitcoinKit.send(
                address,
                amount,
                feeRate = feeRate,
                sortType = TransactionDataSortType.Shuffle
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}