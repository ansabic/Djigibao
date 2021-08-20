
package com.mite.djigibao.ui.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mite.djigibao.database.entities.TodoItem
import com.mite.djigibao.repository.todo.ITodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoRepository: ITodoRepository
): ViewModel() {
    private val _todos by lazy { MutableLiveData<List<TodoItem>>() }
    val todos: LiveData<List<TodoItem>> get() = _todos

    private val _newTodo by lazy { MutableLiveData<Boolean>() }
    val newTodo: LiveData<Boolean> get() = _newTodo

    fun getItems() = viewModelScope.launch(Dispatchers.IO) {
            _todos.postValue(todoRepository.getAllTodos())
    }
    fun resolveTodo(todo:TodoItem,resolve:Boolean) = viewModelScope.launch (Dispatchers.IO){
        todoRepository.resolveTodos(ids = listOf(todo.id),resolve)
        getItems()
    }
    fun deleteTodo(todo:TodoItem) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.deleteTodoItems(listOf(todo.id))
        getItems()
    }
    fun insertTodo(todo: TodoItem) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.insertTodo(todo)
        getItems()
    }
    fun newTodo() = viewModelScope.launch (Dispatchers.IO){
        _newTodo.postValue(true)
    }

    fun hideEditor() = viewModelScope.launch (Dispatchers.IO){
        _newTodo.postValue(false)
    }
}