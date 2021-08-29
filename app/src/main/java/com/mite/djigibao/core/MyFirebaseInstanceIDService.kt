package com.mite.djigibao.core

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseInstanceIDService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.w("FIREBASE_NEW_TOKEN", p0)
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        Log.w("FIREBASE_MESSAGE_INPUT", p0.toString())
    }

    override fun onMessageSent(p0: String) {
        super.onMessageSent(p0)
        Log.w("FIREBASE_MESSAGE_OUTPUT", p0)
    }

}