package com.bsuir.cryptowallet.ui.auth.wallet

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bsuir.cryptowallet.CryptoApp
import com.bsuir.cryptowallet.R
import com.bsuir.cryptowallet.common.base.BaseFragment
import com.bsuir.cryptowallet.common.delegate.viewBinding
import com.bsuir.cryptowallet.databinding.FragmentCreatingMnemonicBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatingMnemonicFragment : BaseFragment(R.layout.fragment_creating_mnemonic),
    View.OnClickListener {

    private val binding by viewBinding<FragmentCreatingMnemonicBinding>()
    private val viewModel: WalletViewModel by activityViewModels()
    private var isBtnClicked = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.createWallet(CryptoApp.instance)
        setupListener()
        setupObserver()
    }

    private fun setupListener() {
        binding.btnCreateWallet.setOnClickListener(this)
        binding.tvCopy.setOnClickListener(this)
    }

    private fun setupObserver() {
        viewModel.wallet.observe(viewLifecycleOwner) {
            binding.tvMnemonic.text = it.mnemonic()
        }
        viewModel.isOperationCompleted.observe(viewLifecycleOwner){
            if(it && isBtnClicked) navigate(CreatingMnemonicFragmentDirections.actionCreatingMnemonicFragmentToContactsFragment())
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.INVISIBLE
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnCreateWallet -> isBtnClicked = true
            R.id.tvCopy -> copy()
        }
    }

    private fun copy() {
        val clipboard =
            requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(
            "copied",
            binding.tvMnemonic.text.toString()
        )
        clipboard.setPrimaryClip(clipData)
    }
}