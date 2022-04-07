package com.bsuir.cryptowallet.ui.auth.wallet

import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bsuir.cryptowallet.R
import com.bsuir.cryptowallet.common.base.BaseFragment
import com.bsuir.cryptowallet.common.delegate.viewBinding
import com.bsuir.cryptowallet.databinding.FragmentImportingMnemonicBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImportingMnemonicFragment : BaseFragment(R.layout.fragment_importing_mnemonic),
    View.OnClickListener {

    private val binding by viewBinding<FragmentImportingMnemonicBinding>()
    private val viewModel: WalletViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        setupObserver()
    }

    private fun setupListener() {
        binding.btnImportWallet.setOnClickListener(this)
        binding.tvCopy.setOnClickListener(this)
    }

    private fun setupObserver() {
        viewModel.wallet.observe(viewLifecycleOwner) {
            navigate(ImportingMnemonicFragmentDirections.actionImportingMnemonicFragmentToContactsFragment())
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnImportWallet -> viewModel.importWallet(
                binding.etMnemonic.text.toString()
            )
            R.id.tvCopy -> past()
        }
    }

    private fun past() {
        val clipboard = requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val copied = clipboard.primaryClip
        val item = copied?.getItemAt(0)
        binding.etMnemonic.setText(item?.text.toString())
    }
}