package com.bsuir.cryptowallet.ui.auth

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.bsuir.cryptowallet.R
import com.bsuir.cryptowallet.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment(R.layout.fragment_splash_screen) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            navigate(SplashFragmentDirections.actionSplashFragmentToAboutAppFragment())
        }, 3000)
    }
}