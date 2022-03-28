package com.bsuir.cryptowallet

import android.app.Application
import com.bsuir.data.utils.SessionHolder
import com.bsuir.data.utils.matrix.RoomDisplayNameFallbackProviderImpl
import dagger.hilt.android.HiltAndroidApp
import org.matrix.android.sdk.api.Matrix
import org.matrix.android.sdk.api.MatrixConfiguration

@HiltAndroidApp
class CryptoApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Matrix.initialize(
            context = this,
            matrixConfiguration = MatrixConfiguration(
                roomDisplayNameFallbackProvider = RoomDisplayNameFallbackProviderImpl()
            )
        )
        val matrix = Matrix.getInstance(this)
        val lastSession = matrix.authenticationService().getLastAuthenticatedSession()
        if (lastSession != null) {
            SessionHolder.currentSession = lastSession
            lastSession.open()
            lastSession.startSync(true)
        }
    }
}