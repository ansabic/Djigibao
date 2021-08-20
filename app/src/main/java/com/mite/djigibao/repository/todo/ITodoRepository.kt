package com.mite.djigibao.repository.todo

import com.mite.djigibao.database.entities.TodoItem
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow

@ViewModelScoped
interface ITodoRepository {
    suspend fun getAllTodos(): List<TodoItem>
    suspend fun deleteTodoItems(ids: List<Long>)
    suspend fun resolveTodos(ids: List<Long>,done: Boolean = true)
    suspend fun insertTodo(item: TodoItem)
    suspend fun insertTodoItems(items: List<TodoItem>)
}