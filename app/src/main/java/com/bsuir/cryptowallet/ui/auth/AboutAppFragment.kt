package com.bsuir.cryptowallet.ui.auth

import android.os.Bundle
import android.view.View
import com.bsuir.cryptowallet.R
import com.bsuir.cryptowallet.common.base.BaseFragment
import com.bsuir.cryptowallet.common.delegate.viewBinding
import com.bsuir.cryptowallet.databinding.FragmentAboutAppBinding

class AboutAppFragment : BaseFragment(R.layout.fragment_about_app), View.OnClickListener {

    private val binding by viewBinding<FragmentAboutAppBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnRegister.setOnClickListener(this)
        binding.tvLoginHere.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnRegister -> navigate(AboutAppFragmentDirections.actionAboutAppFragmentToRegistrationFragment())
            R.id.tvLoginHere -> navigate(AboutAppFragmentDirections.actionAboutAppFragmentToLoginFragment())
        }
    }
}