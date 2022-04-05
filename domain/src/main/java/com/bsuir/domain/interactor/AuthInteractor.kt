package com.bsuir.domain.interactor

import com.bsuir.domain.repository.AuthRepository
import com.cometchat.pro.models.User
import javax.inject.Inject

class AuthInteractor @Inject constructor(private val repository: AuthRepository) {

    fun isUserLoggedIn(): Boolean {
        return repository.isUserLoggedIn()
    }

    fun rememberUser(name: String, password: String): Boolean {
        return repository.rememberUser(name, password)
    }

    suspend fun signIn(uid: String): User? {
        return repository.signIn(uid)
    }

    suspend fun signUp(uid: String, name: String): User? {
        return repository.signUp(uid, name)
    }
}