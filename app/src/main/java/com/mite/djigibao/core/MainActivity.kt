package com.mite.djigibao.core

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.google.firebase.messaging.FirebaseMessaging
import com.mite.djigibao.Navigation
import com.mite.djigibao.ui.login.LoginScreen
import com.mite.djigibao.ui.theme.DjigibaoTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var loginScreen: LoginScreen

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseMessaging.getInstance().token
            .addOnSuccessListener {
            }
            .addOnFailureListener {
                Log.w("CHECK_TOKEN", it.message.toString())
            }
            .addOnCanceledListener {
                Log.w("CHECK_TOKEN", "Canceled...")
            }
        super.onCreate(savedInstanceState)
        setContent {
            DjigibaoTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Navigation()
                }
            }
        }
    }
}

