package com.mite.djigibao.repository.todo

import com.mite.djigibao.database.daos.TodoDao
import com.mite.djigibao.database.entities.TodoItem
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao
) : ITodoRepository {
    override suspend fun getAllTodos(): List<TodoItem> {
        return todoDao.getAllTodoItems()
    }

    override suspend fun deleteTodoItems(ids: List<Long>) {
        todoDao.deleteTodoItems(ids)
    }

    override suspend fun resolveTodos(ids: List<Long>, done: Boolean) {
        todoDao.resolveTodoItem(ids,done)
    }

    override suspend fun insertTodo(item: TodoItem) {
        todoDao.insertTodoItem(item)
    }

    override suspend fun insertTodoItems(items: List<TodoItem>) {
       insertTodoItems(items)
    }
}