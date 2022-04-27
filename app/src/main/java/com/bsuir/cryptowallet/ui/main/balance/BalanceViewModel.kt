package com.bsuir.cryptowallet.ui.main.balance

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bsuir.cryptowallet.CryptoApp
import com.bsuir.data.const.KEY_BALANCE
import com.bsuir.data.const.KEY_MNEMONIC
import com.bsuir.data.source.local.SharedPreferencesUtil
import com.bsuir.data.utils.NumberFormatHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.horizontalsystems.bitcoincore.BitcoinCore
import io.horizontalsystems.bitcoincore.core.Bip
import io.horizontalsystems.bitcoincore.models.BalanceInfo
import io.horizontalsystems.bitcoincore.models.TransactionDataSortType
import io.horizontalsystems.bitcoincore.models.TransactionFilterType
import io.horizontalsystems.bitcoincore.models.TransactionInfo
import io.horizontalsystems.bitcoinkit.BitcoinKit
import io.horizontalsystems.ethereumkit.core.EthereumKit
import io.reactivex.disposables.CompositeDisposable
import wallet.core.jni.HDWallet
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
    private var transactionFilterType: TransactionFilterType? = null

    fun start() {
        bitcoinKit = BitcoinKit(
            CryptoApp.instance,
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

    fun saveBalance(balance: Long) {
        shared.saveData(
            context,
            KEY_BALANCE,
            NumberFormatHelper.cryptoAmountFormat.format(balance / 100_000_000.0)
        )
    }

    override fun onBalanceUpdate(balance: BalanceInfo) {
        isOperationCompleted.postValue(true)
        saveBalance(balance.spendable)
        this.balance.postValue(balance)
    }

    override fun onTransactionsUpdate(
        inserted: List<TransactionInfo>,
        updated: List<TransactionInfo>
    ) {
        setTransactionFilterType(transactionFilterType)
        setOutgoing()
    }

    fun setTransactionFilterType(transactionFilterType: TransactionFilterType?) {
        bitcoinKit.transactions(type = TransactionFilterType.Incoming)
            .subscribe { txList: List<TransactionInfo> ->
                receivedTransaction.postValue(txList.first())
            }
    }

    fun setOutgoing(){
        bitcoinKit.transactions(type = TransactionFilterType.Outgoing)
            .subscribe { txList: List<TransactionInfo> ->
                sendTransaction.postValue(txList.first())
            }
    }

    fun getAddress() = receivedAddress.postValue(bitcoinKit.receiveAddress())

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