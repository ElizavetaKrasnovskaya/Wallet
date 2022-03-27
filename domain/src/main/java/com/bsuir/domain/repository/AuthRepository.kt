package com.bsuir.domain.repository

import android.content.Context
import com.bsuir.domain.model.Result
import org.matrix.android.sdk.api.session.Session
import org.matrix.android.sdk.api.session.user.model.User

interface AuthRepository {

    fun isUserLoggedIn(): Boolean

    fun rememberUser(name: String, password: String): Boolean

    suspend fun signIn(context: Context, name: String, password: String): Result<Session?>

    suspend fun signUp(context: Context, name: String, password: String): Result<Session?>
}