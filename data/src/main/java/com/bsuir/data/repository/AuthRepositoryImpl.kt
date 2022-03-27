package com.bsuir.data.repository

import android.content.Context
import android.net.Uri
import com.bsuir.data.const.SERVER
import com.bsuir.data.source.local.SharedPreferencesDataSource
import com.bsuir.data.utils.SessionHolder
import com.bsuir.data.utils.safeApiCall
import com.bsuir.domain.error.ApplicationError
import com.bsuir.domain.model.Result
import com.bsuir.domain.repository.AuthRepository
import org.matrix.android.sdk.api.Matrix
import org.matrix.android.sdk.api.auth.data.HomeServerConnectionConfig
import org.matrix.android.sdk.api.session.Session
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val sharedPreferences: SharedPreferencesDataSource) :
    AuthRepository {

    private val homeServerConnectionConfig by lazy {
        try {
            HomeServerConnectionConfig
                .Builder()
                .withHomeServerUri(Uri.parse(SERVER))
                .build()
        } catch (failure: Throwable) {
            null
        }
    }

    override fun isUserLoggedIn(): Boolean {
        return sharedPreferences.fetchUserName()
            .isNotEmpty() && sharedPreferences.fetchUserPassword().isNotEmpty()
    }

    override fun rememberUser(name: String, password: String): Boolean {
        sharedPreferences.saveUserName(name)
        sharedPreferences.savePassword(password)
        return sharedPreferences.fetchUserName()
            .isNotEmpty() && sharedPreferences.fetchUserPassword().isNotEmpty()
    }

    override suspend fun signIn(
        context: Context,
        name: String,
        password: String
    ): Result<Session?> {
        var sessionResult: Session? = null
        try {
            Matrix.getInstance(context).authenticationService()
                .directAuthentication(
                    homeServerConnectionConfig!!,
                    name,
                    password,
                    "matrix-sdk-android2-sample"
                )

        } catch (failure: Throwable) {
            null
        }?.let { session ->
            SessionHolder.currentSession = session
            session.open()
            session.startSync(true)
            sessionResult = session
            session
        }
        return safeApiCall { sessionResult }
    }

    override suspend fun signUp(
        context: Context,
        name: String,
        password: String
    ): Result<Session?> {
        TODO("Not yet implemented")
    }
}