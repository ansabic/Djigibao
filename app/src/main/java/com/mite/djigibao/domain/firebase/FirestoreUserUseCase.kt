package com.mite.djigibao.domain.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.mite.djigibao.core.FIRESTORE_USERS
import com.mite.djigibao.database.entities.User
import com.mite.djigibao.repository.users.IUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume

class FirestoreUserUseCase @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val userRepository: IUserRepository
) {

    suspend fun addUser(user: User) = withContext(Dispatchers.IO) {
        return@withContext suspendCancellableCoroutine<Boolean> { continuation ->
            firestore
                .collection(FIRESTORE_USERS)
                .document(user.username)
                .set(user)
                .addOnSuccessListener {
                    continuation.resume(true)
                }
                .addOnCanceledListener {
                    continuation.cancel()
                }
        }

    }

    suspend fun syncUsers() {
        val local = userRepository.getAllUsers()
        val remote = getUsers()
        val resultList = mutableListOf<User>()
        remote.forEach {
            if (local?.map { user -> user.username }?.contains(it.username) == false)
                resultList.add(it)
        }
        userRepository.insertUsers(resultList.toList())
    }

    private suspend fun getUsers(): List<User> {
        return suspendCancellableCoroutine { continuation ->
            firestore
                .collection(FIRESTORE_USERS)
                .get()
                .addOnSuccessListener {
                    val data = it.documents
                    val resultList = mutableListOf<User>()
                    data.forEach { document ->
                        val user = document.toObject(User::class.java)
                        user?.let { author ->
                            resultList.add(
                                author
                            )
                        }
                    }
                    continuation.resume(resultList.toList())

                }
                .addOnCanceledListener {
                    continuation.cancel()
                }
                .addOnFailureListener {
                    continuation.cancel()
                }
        }

    }
}