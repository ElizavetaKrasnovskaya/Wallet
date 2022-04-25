package com.bsuir.cryptowallet.ui.main.settings

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bsuir.data.const.KEY_MNEMONIC
import com.bsuir.data.const.KEY_USER_PASSWORD
import com.bsuir.data.source.local.SharedPreferencesUtil
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.models.User
import com.cometchat.pro.uikit.ui_resources.constants.UIKitConstants
import com.cometchat.pro.uikit.ui_resources.utils.ErrorMessagesUtils
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val shared: SharedPreferencesUtil<String>,
    @ApplicationContext private val context: Context
) : ViewModel() {

    val user = MutableLiveData<User>()
    val deleted = MutableLiveData<Boolean>()
    val out = MutableLiveData<Boolean>()

    fun getCurrentUser() {
        user.postValue(CometChat.getLoggedInUser())
    }

    fun deleteAccount() {
        val user = FirebaseAuth.getInstance().currentUser
        val credential = EmailAuthProvider.getCredential(
            user!!.email.toString(),
            shared.fetchData(context, KEY_USER_PASSWORD, "")
        )
        user?.reauthenticate(credential)?.addOnCompleteListener {
            user.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        CometChat.logout(object : CometChat.CallbackListener<String?>() {
                            override fun onSuccess(s: String?) {
                                deleted.postValue(true)
                            }

                            override fun onError(e: CometChatException) {
                                ErrorMessagesUtils.cometChatErrorMessage(context, e.code)
                            }
                        })
                    }
                }
        }
    }

    fun logout() {
        CometChat.logout(object : CometChat.CallbackListener<String?>() {
            override fun onSuccess(s: String?) {
                shared.saveData(context, KEY_MNEMONIC, "")
                out.postValue(true)
            }

            override fun onError(e: CometChatException) {
            }
        })
    }

    fun updateName(name: String) {
        val authkey = UIKitConstants.AppInfo.AUTH_KEY
        val user = CometChat.getLoggedInUser()
        user.name = name
        CometChat.updateUser(user, authkey, object : CometChat.CallbackListener<User?>() {
            override fun onSuccess(user: User?) {

            }

            override fun onError(e: CometChatException) {
                if (context != null)
                    ErrorMessagesUtils.cometChatErrorMessage(context, e.code)
            }
        })
    }

    fun updatePassword(password: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val credential = EmailAuthProvider
            .getCredential(
                user!!.email.toString(),
                shared.fetchData(context, KEY_USER_PASSWORD, "")
            )
        user.reauthenticate(credential)
            .addOnCompleteListener {
                user.updatePassword(password).addOnCompleteListener {
                }
            }
    }

    fun showMnemonic(): String {
        return shared.fetchData(context, KEY_MNEMONIC, "")
    }
}