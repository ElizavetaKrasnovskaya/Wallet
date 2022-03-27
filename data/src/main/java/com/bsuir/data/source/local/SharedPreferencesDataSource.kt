package com.bsuir.data.source.local

import android.app.Application
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.bsuir.data.const.KEY_USER_NAME
import com.bsuir.data.const.KEY_USER_PASSWORD
import com.bsuir.data.const.PREFS_NAME
import com.bsuir.data.utils.get
import com.bsuir.data.utils.put
import javax.inject.Inject

class SharedPreferencesDataSource @Inject constructor(private val app: Application) {

    private val sharedPrefs by lazy {
        val masterKey = MasterKey.Builder(app)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            app,
            PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveUserName(userName: String) {
        sharedPrefs.put(KEY_USER_NAME, userName)
    }

    fun savePassword(password: String) {
        sharedPrefs.put(KEY_USER_PASSWORD, password)
    }

    fun fetchUserName(): String {
        return sharedPrefs.get(KEY_USER_NAME, "")
    }

    fun fetchUserPassword(): String {
        return sharedPrefs.get(KEY_USER_PASSWORD, "")
    }
}