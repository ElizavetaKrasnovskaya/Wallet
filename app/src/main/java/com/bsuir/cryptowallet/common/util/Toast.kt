package com.bsuir.cryptowallet.common.util

import android.content.Context
import android.widget.Toast

fun Context.toast(message: String) {
    val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    toast.show()
}