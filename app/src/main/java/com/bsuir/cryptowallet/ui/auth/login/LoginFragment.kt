package com.bsuir.cryptowallet.ui.auth.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bsuir.cryptowallet.R
import com.bsuir.cryptowallet.common.base.BaseFragment
import com.bsuir.cryptowallet.common.delegate.viewBinding
import com.bsuir.cryptowallet.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment(R.layout.fragment_login), View.OnClickListener {

    private val viewModel: LoginViewModel by viewModels()
    private val binding by viewBinding<FragmentLoginBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.session.observe(viewLifecycleOwner) {
            toast("Cool")
        }
        viewModel.error.observe(viewLifecycleOwner) {
            toast(it.toString())
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnLogin -> viewModel.signIn(
                binding.etName.text.toString(),
                binding.etPassword.text.toString()
            )
        }
    }

}