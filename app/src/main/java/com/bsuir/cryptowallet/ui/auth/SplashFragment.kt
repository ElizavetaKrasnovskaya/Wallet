package com.bsuir.cryptowallet.ui.auth

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.bsuir.cryptowallet.R
import com.bsuir.cryptowallet.common.base.BaseFragment
import com.cometchat.pro.core.CometChat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment(R.layout.fragment_splash_screen) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            try {
                val user = CometChat.getLoggedInUser()
                if(user != null) navigate(SplashFragmentDirections.actionSplashFragmentToContactsFragment())
                else navigate(SplashFragmentDirections.actionSplashFragmentToAboutAppFragment())
            } catch(e: Exception) {
                navigate(SplashFragmentDirections.actionSplashFragmentToAboutAppFragment())
            }
        }, 3000)
    }
}