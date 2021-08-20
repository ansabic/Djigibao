package com.mite.djigibao

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mite.djigibao.ui.Destination
import com.mite.djigibao.ui.lista_pisama.SongListScreen
import com.mite.djigibao.ui.lista_pisama.SongListViewModel
import com.mite.djigibao.ui.login.LoginScreen
import com.mite.djigibao.ui.login.LoginViewModel
import com.mite.djigibao.ui.main.MainScreen
import com.mite.djigibao.ui.main.MainViewModel
import com.mite.djigibao.ui.new_song.NewSongScreen
import com.mite.djigibao.ui.new_song.NewSongViewModel
import com.mite.djigibao.ui.todo.TodoScreen
import com.mite.djigibao.ui.todo.TodoViewModel

@ExperimentalAnimationApi
@Preview
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destination.Login.name
    ) {

        composable(route = Destination.Login.name) {
            val viewModel = hiltViewModel<LoginViewModel>()
            LoginScreen().LoginScreen(viewModel, navController)
        }
        composable(route = Destination.MainScreen.name) {
            val viewModel = hiltViewModel<MainViewModel>()
            MainScreen().MainScreen(viewModel, navController)
        }
        composable(route = Destination.SongList.name) {
            val viewModel = hiltViewModel<SongListViewModel>()
            SongListScreen().SongList(viewModel, navController)
        }
        composable(route = Destination.NewSong.name) {
            val viewModel = hiltViewModel<NewSongViewModel>()
            NewSongScreen().NewSong(viewModel, navController)
        }
        composable(route = Destination.Todo.name) {
            val viewModel = hiltViewModel<TodoViewModel>()
            TodoScreen().TodoScreen(viewModel,navController)
        }
    }
}