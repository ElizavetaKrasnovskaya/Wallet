package com.bsuir.cryptowallet.ui.auth.registration

import android.os.Bundle
import android.view.View
import com.bsuir.cryptowallet.R
import com.bsuir.cryptowallet.common.base.BaseFragment
import com.bsuir.cryptowallet.common.delegate.viewBinding
import com.bsuir.cryptowallet.databinding.FragmentRegistrationBinding

class RegistrationFragment : BaseFragment(R.layout.fragment_registration), View.OnClickListener {

    private val binding by viewBinding<FragmentRegistrationBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners(){

    }

    override fun onClick(view: View) {

    }

}