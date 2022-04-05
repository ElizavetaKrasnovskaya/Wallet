package com.bsuir.cryptowallet.ui.auth.registration

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bsuir.cryptowallet.R
import com.bsuir.cryptowallet.common.base.BaseFragment
import com.bsuir.cryptowallet.common.delegate.viewBinding
import com.bsuir.cryptowallet.databinding.FragmentRegistrationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : BaseFragment(R.layout.fragment_registration), View.OnClickListener {

    private val binding by viewBinding<FragmentRegistrationBinding>()
    private val viewModel: RegistrationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObserver()
    }

    private fun setupListeners() {
        binding.btnRegister.setOnClickListener(this)
    }

    private fun setupObserver() {
        viewModel.user.observe(viewLifecycleOwner) {
            if (it != null) navigate(RegistrationFragmentDirections.actionRegistrationFragmentToWalletInfoFragment())
        }
        viewModel.error.observe(viewLifecycleOwner) {
            if (it != null) toast("error")
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnRegister -> viewModel.signUp(
                binding.etName.text.toString(),
                binding.etPassword.text.toString()
            )
        }
    }
}