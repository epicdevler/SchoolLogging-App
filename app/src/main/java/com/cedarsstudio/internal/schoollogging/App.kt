package com.cedarsstudio.internal.schoollogging

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)?.setAutomaticResourceManagementEnabled(true)
//        val firebaseAppCheck = FirebaseAppCheck.getInstance()
//        firebaseAppCheck.installAppCheckProviderFactory(
//            PlayIntegrityAppCheckProviderFactory.getInstance()
//        )

    }
}