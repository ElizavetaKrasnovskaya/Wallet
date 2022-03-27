package com.bsuir.cryptowallet.ui.auth.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsuir.domain.error.ApplicationError
import com.bsuir.domain.interactor.AuthInteractor
import com.bsuir.domain.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.session.Session
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val interactor: AuthInteractor,
    @ApplicationContext private val context: Context
) : ViewModel() {

    val session = MutableLiveData<Session>()
    val error = MutableLiveData<ApplicationError>()

    private fun signIn(name: String, password: String) {
        viewModelScope.launch {
            when(val result = interactor.signIn(context, name, password)){
                is Result.Success -> session.postValue(result.value!!)
                is Result.Error -> error.postValue(result.error)
            }

        }
    }

}