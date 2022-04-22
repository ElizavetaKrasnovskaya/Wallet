package com.bsuir.cryptowallet.ui.main.balance

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.activityViewModels
import com.bsuir.cryptowallet.R
import com.bsuir.cryptowallet.common.base.BaseFragment
import com.bsuir.cryptowallet.common.delegate.viewBinding
import com.bsuir.cryptowallet.databinding.FragmentBalanceBinding
import com.bsuir.cryptowallet.ui.auth.wallet.WalletViewModel
import com.bsuir.data.const.KEY_BALANCE
import com.bsuir.data.source.local.SharedPreferencesUtil
import com.bsuir.data.utils.NumberFormatHelper
import com.bsuir.domain.model.FeePriority
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import dagger.hilt.android.AndroidEntryPoint
import io.horizontalsystems.bitcoincore.models.TransactionFilterType
import java.text.DateFormat
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class BalanceFragment : BaseFragment(R.layout.fragment_balance), View.OnClickListener {

    private val binding by viewBinding<FragmentBalanceBinding>()
    private val viewModel: WalletViewModel by activityViewModels()

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private var address: String = ""
    private var amount: Long = 0
    private var feeRate: Int = 10
    private val delimeter = 100_000_000.0

    @Inject
    lateinit var shared: SharedPreferencesUtil<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvBalance.text = "${shared.fetchData(requireContext(), KEY_BALANCE, "")} BTC"
        viewModel.start()
        setupObserver()
        viewModel.setTransactionFilterType(TransactionFilterType.Outgoing)
        setupClickListener()
    }

    private fun setupClickListener() {
        binding.btnReceive.setOnClickListener(this)
        binding.btnSend.setOnClickListener(this)
    }

    private fun setupObserver() {
        viewModel.balance.observe(viewLifecycleOwner) {
            viewModel.saveBalance(it.spendable)
            if (it != null && it.spendable.toString().isNotEmpty()) binding.tvBalance.text =
                NumberFormatHelper.cryptoAmountFormat.format(it.spendable / 100_000_000.0)
        }
        viewModel.sendTransaction.observe(viewLifecycleOwner) {
            binding.tvSendAmount.text =
                "${NumberFormatHelper.cryptoAmountFormat.format(it.amount / delimeter)} BTC"
            binding.tvSendDate.text = formatDate(it.timestamp)
        }
        viewModel.receivedTransaction.observe(viewLifecycleOwner) {
            binding.tvReceivedAmount.text =
                "${NumberFormatHelper.cryptoAmountFormat.format(it.amount / delimeter)} BTC"
            binding.tvReceivedDate.text = formatDate(it.timestamp)
        }
        viewModel.receivedAddress.observe(viewLifecycleOwner) {
            showAlert(it)
        }
    }

    private fun formatDate(timestamp: Long): String {
        return DateFormat.getInstance().format(Date(timestamp * 1000))
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnReceive -> viewModel.getAddress()
            R.id.btnSend -> showBottomSheet()
            R.id.btnSendCrypto -> {
                viewModel.sendBTC(
                    bottomSheetDialog.findViewById<EditText>(R.id.etAddress)?.text.toString(),
                    (bottomSheetDialog.findViewById<EditText>(R.id.etAmount)?.text.toString()
                        .toDouble() * delimeter).toLong(),
                    feeRate
                )
                bottomSheetDialog.hide()
            }
        }
    }

    private fun showBottomSheet() {
        val wrapperContext =
            ContextThemeWrapper(context, R.style.ThemeOverlay_Demo_BottomSheetDialog)
        bottomSheetDialog = BottomSheetDialog(wrapperContext)
        bottomSheetDialog.setContentView(R.layout.custom_send)
        bottomSheetDialog.findViewById<AppCompatButton>(R.id.btnSendCrypto)
            ?.setOnClickListener(this)
        (bottomSheetDialog.findViewById<Spinner>(R.id.spinner) as Spinner).onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    itemSelected: View, selectedItemPosition: Int, selectedId: Long
                ) {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        Color.WHITE
                    )
                    when (selectedItemPosition) {
                        0 -> feeRate = FeePriority.Low.feeRate
                        1 -> feeRate = FeePriority.Medium.feeRate
                        2 -> feeRate = FeePriority.High.feeRate
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        bottomSheetDialog.show()
    }

    private fun showAlert(address: String) {
        val image = ImageView(requireContext())
        image.setImageBitmap(getImage(address))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setMessage(address)
        builder.setPositiveButton(
            "OK"
        ) { dialog, _ ->
            dialog.dismiss()
        }
        builder.setView(image)
        builder.create().show()
    }

    private fun getImage(content: String): Bitmap {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }
        return bitmap
    }
}