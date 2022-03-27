package com.bsuir.cryptowallet.common.util

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class AlertDialogUtil(private val title: String, private val message: String) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton(
                "OK"
            ) { dialog, _ ->
                dialog.cancel()
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}