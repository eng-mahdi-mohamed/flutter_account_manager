package com.itcasd.flutter_account_manager

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder

class AuthenticatorService : Service() {

    private lateinit var authenticator : Authenticator

    override fun onBind(intent: Intent?): IBinder {
        return authenticator.getIBinder()
    }

    override fun onCreate() {
        authenticator = Authenticator(this);
    }
}