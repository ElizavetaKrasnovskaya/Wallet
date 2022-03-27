package com.bsuir.cryptowallet.ui.auth.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bsuir.cryptowallet.R
import com.bsuir.cryptowallet.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment(R.layout.fragment_login), View.OnClickListener {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onClick(view: View) {
        when(view.id){

        }
    }

}