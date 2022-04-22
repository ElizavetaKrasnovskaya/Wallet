package com.bsuir.cryptowallet.ui.auth.wallet

import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.bsuir.cryptowallet.CryptoApp
import com.bsuir.cryptowallet.R
import com.bsuir.cryptowallet.common.base.BaseFragment
import com.bsuir.cryptowallet.common.delegate.viewBinding
import com.bsuir.cryptowallet.databinding.FragmentImportingMnemonicBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImportingMnemonicFragment : BaseFragment(R.layout.fragment_importing_mnemonic),
    View.OnClickListener {

    private val binding by viewBinding<FragmentImportingMnemonicBinding>()
    private val viewModel: WalletViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        setupObserver()
    }

    private fun setupListener() {
        binding.btnImportWallet.setOnClickListener(this)
        binding.tvPaste.setOnClickListener(this)
    }

    private fun setupObserver() {
        viewModel.isOperationCompleted.observe(viewLifecycleOwner) {
            if (it) navigate(ImportingMnemonicFragmentDirections.actionImportingMnemonicFragmentToContactsFragment())
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.INVISIBLE
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnImportWallet -> {
                viewModel.importWallet(
                    binding.etMnemonic.text.toString(),
                    CryptoApp.instance
                )
            }
            R.id.tvPaste -> paste()
        }
    }

    private fun paste() {
        val clipboard = requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val copied = clipboard.primaryClip
        val item = copied?.getItemAt(0)
        binding.etMnemonic.setText(item?.text.toString())
    }
}