package com.mite.djigibao.domain.firebase

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume

class FcmUseCase @Inject constructor(

) {
    suspend fun getToken() = withContext(Dispatchers.IO) {
        return@withContext suspendCancellableCoroutine<String?> { continuation ->
            FirebaseMessaging.getInstance().token
                .addOnSuccessListener {
                    continuation.resume(it)
                }
                .addOnCanceledListener {
                    continuation.resume(null)
                }
                .addOnFailureListener {
                    continuation.resume(null)
                }
        }
    }
}