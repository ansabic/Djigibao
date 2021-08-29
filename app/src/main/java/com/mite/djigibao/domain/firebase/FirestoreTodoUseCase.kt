package com.mite.djigibao.domain.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.mite.djigibao.core.FIRESTORE_TODO
import com.mite.djigibao.database.entities.TodoItem
import com.mite.djigibao.repository.todo.ITodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume

class FirestoreTodoUseCase @Inject constructor(
    private val todoRepository: ITodoRepository,
    private val firestore: FirebaseFirestore
) {

    suspend fun addTodo(todo: TodoItem) = withContext(Dispatchers.IO) {
        return@withContext suspendCancellableCoroutine<Boolean> { continuation ->
            firestore
                .collection(FIRESTORE_TODO)
                .document(todo.text)
                .set(todo)
                .addOnSuccessListener {
                    continuation.resume(true)
                }
                .addOnCanceledListener {
                    continuation.cancel()
                }
        }

    }

    suspend fun syncTodo() {
        val local = todoRepository.getAllTodos()
        val remote = getTodos()
        val resultList = mutableListOf<TodoItem>()
        remote.forEach {
            if (!local.map { todo -> todo.text }.contains(it.text))
                resultList.add(it)
        }
        todoRepository.insertTodoItems(resultList.toList())
    }

    private suspend fun getTodos(): List<TodoItem> {
        return suspendCancellableCoroutine { continuation ->
            firestore
                .collection(FIRESTORE_TODO)
                .get()
                .addOnSuccessListener {
                    val data = it.documents
                    val resultList = mutableListOf<TodoItem>()
                    data.forEach { document ->
                        val todo = document.toObject(TodoItem::class.java)
                        todo?.let { todoItem ->
                            resultList.add(
                                todoItem
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

    suspend fun updateTodo(todo: TodoItem, resolve: Boolean) = withContext(Dispatchers.IO) {
        return@withContext suspendCancellableCoroutine<Boolean> { continuation ->
            firestore
                .collection(FIRESTORE_TODO)
                .document(todo.text)
                .update("done", resolve)
                .addOnSuccessListener {
                    continuation.resume(true)
                }
                .addOnCanceledListener {
                    continuation.cancel()
                }
        }
    }
}