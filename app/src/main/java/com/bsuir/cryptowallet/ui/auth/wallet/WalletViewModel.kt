package com.bsuir.cryptowallet.ui.auth.wallet

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bsuir.cryptowallet.CryptoApp
import com.bsuir.data.const.KEY_BALANCE
import com.bsuir.data.const.KEY_MNEMONIC
import com.bsuir.data.source.local.SharedPreferencesUtil
import com.bsuir.data.utils.NumberFormatHelper
import com.bsuir.domain.interactor.WalletInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.horizontalsystems.bitcoincore.BitcoinCore
import io.horizontalsystems.bitcoincore.core.Bip
import io.horizontalsystems.bitcoincore.models.BalanceInfo
import io.horizontalsystems.bitcoincore.models.TransactionDataSortType
import io.horizontalsystems.bitcoincore.models.TransactionFilterType
import io.horizontalsystems.bitcoincore.models.TransactionInfo
import io.horizontalsystems.bitcoinkit.BitcoinKit
import io.reactivex.disposables.CompositeDisposable
import wallet.core.jni.HDWallet
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val interactor: WalletInteractor,
    private val shared: SharedPreferencesUtil<String>,
    @ApplicationContext private val context: Context
) : ViewModel(), BitcoinKit.Listener {

    val wallet = MutableLiveData<HDWallet>()
    val balance = MutableLiveData<BalanceInfo>()
    val isOperationCompleted = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
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

    init {
        if (shared.fetchData(context, KEY_MNEMONIC, "").isNotEmpty()) bitcoinKit = BitcoinKit(
            CryptoApp.instance,
            shared.fetchData(context, KEY_MNEMONIC, "").split(" "),
            passphrase,
            walletId,
            networkType,
            syncMode = syncMode,
            bip = bip
        )
    }

    fun createWallet(context: Application) {
        isLoading.postValue(false)
        isLoading.postValue(true)
        wallet.postValue(interactor.createWallet(context))
        start()
        bitcoinKit.start()
        isLoading.postValue(false)
    }

    fun importWallet(mnemonic: String, context: Application) {
        isLoading.postValue(false)
        isLoading.postValue(true)
        wallet.postValue(interactor.importWallet(mnemonic, context))
        start()
        bitcoinKit.start()
        isLoading.postValue(false)
    }

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
    }

    fun setTransactionFilterType(transactionFilterType: TransactionFilterType?) {

        bitcoinKit.transactions(type = TransactionFilterType.Incoming)
            .subscribe { txList: List<TransactionInfo> ->
                receivedTransaction.postValue(txList.first())
            }.let {
                disposables.add(it)
            }

        bitcoinKit.transactions(type = TransactionFilterType.Outgoing)
            .subscribe { txList: List<TransactionInfo> ->
                sendTransaction.postValue(txList.first())
            }.let {
                disposables.add(it)
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