package com.steelsoftware.horoscope

import com.steelsoftware.horoscope.di.DaggerAppComponent
import com.google.android.gms.ads.MobileAds
import com.onesignal.OneSignal
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class HoroscopeApp : DaggerApplication() {


    override fun onCreate() {
        super.onCreate()


        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init()

        // Admob Initialization
        MobileAds.initialize(this, getString(R.string.admob_app_id))

    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }
}