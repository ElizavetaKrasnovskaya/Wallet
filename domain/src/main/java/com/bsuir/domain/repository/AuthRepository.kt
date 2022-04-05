package com.bsuir.domain.repository

import com.cometchat.pro.models.User

interface AuthRepository {

    fun isUserLoggedIn(): Boolean

    fun rememberUser(name: String, password: String): Boolean

    suspend fun signIn(uid: String): User?

    suspend fun signUp(uid: String, name: String): User?
}