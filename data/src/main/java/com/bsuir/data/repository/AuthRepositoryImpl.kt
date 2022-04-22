package com.bsuir.data.repository

import com.bsuir.data.const.AUTH_KEY
import com.bsuir.data.source.local.SharedPreferencesUtil
import com.bsuir.domain.repository.AuthRepository
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.models.User
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferencesUtil<String>
) : AuthRepository {

    override fun isUserLoggedIn(): Boolean {
        return false
    }

    override fun rememberUser(name: String, password: String): Boolean {
//        sharedPreferences.saveUserName(name)
//        sharedPreferences.savePassword(password)
//        return sharedPreferences.fetchUserName()
//            .isNotEmpty() && sharedPreferences.fetchUserPassword().isNotEmpty()
        return false
    }

    override suspend fun signIn(uid: String): User? {
        var result: User? = null
        CometChat.login(uid, AUTH_KEY, object : CometChat.CallbackListener<User>() {
            override fun onSuccess(user: User?) {
                result = user
            }

            override fun onError(p0: CometChatException?) {
                result = null
            }
        })
        return result
    }

    override suspend fun signUp(uid: String, name: String): User? {
        var result: User? = null
        CometChat.createUser(User(uid, name), AUTH_KEY, object : CometChat.CallbackListener<User>(){
            override fun onSuccess(user: User?) {
                result = user
            }

            override fun onError(p0: CometChatException?) {
                result = null
            }
        })
        return result
    }
}