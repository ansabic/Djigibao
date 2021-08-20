package com.mite.djigibao.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mite.djigibao.database.entities.TodoItem
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TodoDao {

    @Query("SELECT * FROM todo")
    abstract fun getAllTodoItems(): List<TodoItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertTodoItem(item: TodoItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertTOdoItems(items: List<TodoItem>)

    @Query("UPDATE todo SET done = :done WHERE id IN (:ids)")
    abstract suspend fun resolveTodoItem(ids: List<Long>, done: Boolean = true)

    @Query("DELETE FROM todo WHERE id IN (:ids)")
    abstract suspend fun deleteTodoItems(ids: List<Long>)
}