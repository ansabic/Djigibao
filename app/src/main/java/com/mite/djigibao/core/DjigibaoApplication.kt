package com.mite.djigibao.core

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DjigibaoApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}