package com.bsuir.cryptowallet.ui.main.settings

import android.app.AlertDialog
import android.app.Application
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import com.bsuir.cryptowallet.CryptoApp
import com.bsuir.cryptowallet.R
import com.bsuir.cryptowallet.common.base.BaseFragment
import com.bsuir.cryptowallet.common.delegate.viewBinding
import com.bsuir.cryptowallet.common.util.SharedPreferencesUtil
import com.bsuir.cryptowallet.databinding.FragmentSettingsBinding
import com.bsuir.data.const.KEY_MNEMONIC
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsFragment : BaseFragment(R.layout.fragment_settings), View.OnClickListener {

    private val binding: FragmentSettingsBinding by viewBinding()
    private val viewModel: SettingsViewModel by viewModels()
    private val shared = SharedPreferencesUtil<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        setupListener()
        viewModel.getCurrentUser()
    }

    private fun setupListener() {
        binding.tvDeleteAccount.setOnClickListener(this)
        binding.tvLogout.setOnClickListener(this)
        binding.tvChangeName.setOnClickListener(this)
        binding.tvChangePassword.setOnClickListener(this)
        binding.tvShowMnemonic.setOnClickListener(this)
    }

    private fun setupObserver() {
        viewModel.user.observe(viewLifecycleOwner) {
            binding.tvName.text = it.name
        }
        viewModel.deleted.observe(viewLifecycleOwner) {
            if (it) navigate(SettingsFragmentDirections.actionSettingsFragmentToSplashFragment())
        }
        viewModel.out.observe(viewLifecycleOwner) {
            if (it) navigate(SettingsFragmentDirections.actionSettingsFragmentToSplashFragment())
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.tvDeleteAccount -> viewModel.deleteAccount()
            R.id.tvLogout -> viewModel.logout()
            R.id.tvChangeName -> showNameAlert()
            R.id.tvChangePassword -> showPasswordAlert()
            R.id.tvShowMnemonic -> showMnemonic()
        }
    }

    private fun showNameAlert() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setMessage("Enter name")
        val input = EditText(requireContext())
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        input.layoutParams = lp
        builder.setView(input)
        builder.setPositiveButton(
            "Change"
        ) { _, _ ->
            viewModel.updateName(input.text.toString())
            binding.tvName.text = input.text.toString()
        }
        builder.show()
    }

    private fun showPasswordAlert() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setMessage("Enter password")
        val input = EditText(requireContext())
        input.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        input.layoutParams = lp
        builder.setView(input)
        builder.setPositiveButton(
            "Change"
        ) { _, _ ->
            viewModel.updatePassword(input.text.toString())
        }
        builder.show()
    }

    private fun showMnemonic() {
        val builder = AlertDialog.Builder(requireActivity())
        val mnemonic = viewModel.showMnemonic()
        print(mnemonic)
        builder.setMessage(viewModel.showMnemonic())
        builder.show()
    }
}