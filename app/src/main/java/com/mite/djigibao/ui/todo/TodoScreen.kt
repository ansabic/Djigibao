package com.mite.djigibao.ui.todo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mite.djigibao.ui.NewTodoItem
import com.mite.djigibao.ui.TodoItem
import com.mite.djigibao.ui.theme.Purple200

class TodoScreen {

    @Composable
    fun TodoScreen(viewModel: TodoViewModel, navController: NavController) {
        viewModel.getItems()
        val items = viewModel.todos.observeAsState()
        val newTodo = viewModel.newTodo.observeAsState(false)
        Scaffold(
            modifier = Modifier
                .fillMaxSize()

        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Purple200)
            ) {
                Spacer(
                    modifier = Modifier
                        .padding(vertical = 30.dp)
                )
                LazyColumn(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(horizontal = 5.dp)
                ) {
                    items.value?.forEach { todoItem ->
                        item {
                            TodoItem(
                                resolved = todoItem.done,
                                text = todoItem.text,
                                onCheck = {
                                    viewModel.resolveTodo(todoItem, it)
                                },
                                deleteTodo = {
                                    viewModel.deleteTodo(todoItem)
                                }
                            )
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .padding(end = 25.dp, bottom = 25.dp)
            )
            {
                if (newTodo.value) {
                    NewTodoItem {
                        viewModel.insertTodo(it)
                        viewModel.hideEditor()
                    }
                }
                FloatingActionButton(
                    onClick = {
                        viewModel.newTodo()
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                ) {
                    Image(
                        painter = painterResource(android.R.drawable.ic_input_add),
                        colorFilter = ColorFilter.tint(Color.White),
                        contentDescription = ""
                    )

                }
            }

        }


    }
}