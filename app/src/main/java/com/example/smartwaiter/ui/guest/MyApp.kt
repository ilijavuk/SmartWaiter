package com.example.smartwaiter.ui.guest

import android.app.Application
import com.stripe.android.PaymentConfiguration

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51I8lIrDfSGopQFLzayysE0nRL1gCKrKPlfNwLSLo5QtYlT65AGOA2XLq1Nny9XFilHdFHwmtRsZIb86hAcswkEkw00sGDSSCMk"
        )
    }
}