package com.education.jcc

import android.app.Application
import net.payrdr.mobile.payment.sdk.form.SDKConfigBuilder
import net.payrdr.mobile.payment.sdk.form.SDKForms
import net.payrdr.mobile.payment.sdk.form.component.impl.RemoteCardInfoProvider
import net.payrdr.mobile.payment.sdk.form.component.impl.RemoteKeyProvider

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SDKForms.init(
            SDKConfigBuilder()
                .keyProvider(
                    RemoteKeyProvider("https://gateway-test.jcc.com.cy/payment/se/keys.do")
                )
                .cardInfoProvider(
                    RemoteCardInfoProvider(
                        url = "https://mrbin.io/bins/display",
                        urlBin = "https://mrbin.io/bins/"
                    )
                ).build()
        )
    }
    }
