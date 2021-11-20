package com.cookandroid.eatwithme

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "4e82ec43b7d6bf641f2af26b87d17364")
    }
}