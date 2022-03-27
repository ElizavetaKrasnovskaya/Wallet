package com.bsuir.cryptowallet.common.util

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}