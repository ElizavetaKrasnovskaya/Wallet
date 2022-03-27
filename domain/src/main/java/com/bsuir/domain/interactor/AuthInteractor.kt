package com.bsuir.domain.interactor

import android.content.Context
import com.bsuir.domain.model.Result
import com.bsuir.domain.repository.AuthRepository
import org.matrix.android.sdk.api.session.Session
import org.matrix.android.sdk.api.session.user.model.User
import javax.inject.Inject

class AuthInteractor @Inject constructor(private val repository: AuthRepository) {

    fun isUserLoggedIn(): Boolean {
        return repository.isUserLoggedIn()
    }

    fun rememberUser(name: String, password: String): Boolean {
        return repository.rememberUser(name, password)
    }

    suspend fun signIn(context: Context, name: String, password: String): Result<Session?> {
        return repository.signIn(context, name, password)
    }

    suspend fun signUp(context: Context, name: String, password: String): Result<Session?> {
        return repository.signUp(context, name, password)
    }
}