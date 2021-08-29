package com.mite.djigibao.domain.firebase

import javax.inject.Inject

class FirestoreSync @Inject constructor(
    private val firestoreUserUseCase: FirestoreUserUseCase,
    private val firestoreEventUseCase: FirestoreEventUseCase,
    private val firestoreSongUseCase: FirestoreSongUseCase,
    private val firestoreTodoUseCase: FirestoreTodoUseCase
) {
    suspend fun sync() {
        firestoreEventUseCase.syncEvents()
        firestoreSongUseCase.syncSongs()
        firestoreUserUseCase.syncUsers()
        firestoreTodoUseCase.syncTodo()
    }
}