package com.bsuir.cryptowallet.ui.auth.login

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsuir.data.const.AUTH_KEY
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.models.User
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    val userLiveData = MutableLiveData<User?>()
    val error = MutableLiveData<CometChatException?>()

    private var firebaseAuth = FirebaseAuth.getInstance()

    fun signIn(activity: Activity, email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    viewModelScope.launch {
                        CometChat.login(
                            user!!.uid,
                            AUTH_KEY,
                            object : CometChat.CallbackListener<User>() {
                                override fun onSuccess(u: User?) {
                                    userLiveData.postValue(u)
                                }

                                override fun onError(ex: CometChatException?) {
                                    error.postValue(ex)
                                }
                            })
                    }
                }
            }
    }
}