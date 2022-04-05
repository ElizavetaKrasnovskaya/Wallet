package com.bsuir.cryptowallet.ui.auth.wallet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bsuir.domain.interactor.WalletInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import wallet.core.jni.HDWallet
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val interactor: WalletInteractor
) : ViewModel() {

    val wallet = MutableLiveData<HDWallet>()

    fun createWallet() {
        wallet.postValue(interactor.createWallet())
    }

    fun importWallet(mnemonic: String) {
        wallet.postValue(interactor.importWallet(mnemonic))
    }
}