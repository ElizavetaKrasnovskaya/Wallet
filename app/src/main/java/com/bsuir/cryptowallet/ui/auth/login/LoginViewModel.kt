package com.bsuir.cryptowallet.ui.auth.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsuir.data.const.AUTH_KEY
import com.bsuir.domain.interactor.AuthInteractor
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val interactor: AuthInteractor
) : ViewModel() {

    val user = MutableLiveData<User?>()
    val error = MutableLiveData<CometChatException?>()

    fun signIn(uid: String) {
        viewModelScope.launch {
            CometChat.login(uid, AUTH_KEY, object : CometChat.CallbackListener<User>() {
                override fun onSuccess(u: User?) {
                    user.postValue(u)
                }

                override fun onError(ex: CometChatException?) {
                    error.postValue(ex)
                }
            })
        }
    }
}