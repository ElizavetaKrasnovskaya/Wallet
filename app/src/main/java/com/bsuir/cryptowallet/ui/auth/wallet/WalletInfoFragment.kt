package com.bsuir.cryptowallet.ui.auth.wallet

import android.os.Bundle
import android.view.View
import com.bsuir.cryptowallet.R
import com.bsuir.cryptowallet.common.base.BaseFragment
import com.bsuir.cryptowallet.common.delegate.viewBinding
import com.bsuir.cryptowallet.databinding.FragmentWalletInfoBinding
import dagger.hilt.android.AndroidEntryPoint

class WalletInfoFragment : BaseFragment(R.layout.fragment_wallet_info), View.OnClickListener {

    private val binding by viewBinding<FragmentWalletInfoBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
    }

    private fun setupListener() {
        binding.btnCreateWallet.setOnClickListener(this)
        binding.tvHaveWallet.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnCreateWallet -> navigate(WalletInfoFragmentDirections.actionWalletInfoFragmentToCreatingMnemonicFragment())
            R.id.tvHaveWallet -> navigate(WalletInfoFragmentDirections.actionWalletInfoFragmentToImportingMnemonicFragment())
        }
    }
}