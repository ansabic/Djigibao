package com.mite.djigibao

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.mite.djigibao.database.entities.Song
import com.mite.djigibao.database.entities.User
import com.mite.djigibao.ui.calendar.CalendarScreen
import com.mite.djigibao.ui.calendar.CalendarViewModel
import com.mite.djigibao.ui.edit_song.EditSongScreen
import com.mite.djigibao.ui.edit_song.EditSongViewModel
import com.mite.djigibao.ui.login.LoginScreen
import com.mite.djigibao.ui.login.LoginViewModel
import com.mite.djigibao.ui.main.MainScreen
import com.mite.djigibao.ui.main.MainViewModel
import com.mite.djigibao.ui.new_song.NewSongScreen
import com.mite.djigibao.ui.new_song.NewSongViewModel
import com.mite.djigibao.ui.song_list.SongListScreen
import com.mite.djigibao.ui.song_list.SongListViewModel
import com.mite.djigibao.ui.todo.TodoScreen
import com.mite.djigibao.ui.todo.TodoViewModel
import java.time.ZonedDateTime

sealed class Destination(val name: String) {
    object Login : Destination("login")
    object MainScreen : Destination("main_screen")
    object SongList : Destination("song_list")
    object NewSong : Destination("new_song")
    object Todo : Destination("todo")
    object Calendar : Destination("calendar")
    object EditSong : Destination("edit_song")
}

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
            TodoScreen().TodoScreen(viewModel, navController)
        }
        composable(route = Destination.Calendar.name) {
            val viewModel = hiltViewModel<CalendarViewModel>()
            CalendarScreen().Calendar(viewModel, navController)
        }
        composable(
            route = Destination.EditSong.name,
            arguments = listOf(navArgument("song") {
                nullable = false
                type = NavType.inferFromValueType(
                    Song(
                        creationDate = ZonedDateTime.now(),
                        user = User()
                    )
                )
                build()
            })
        ) {
            val viewModel = hiltViewModel<EditSongViewModel>()
            EditSongScreen().EditSong(viewModel, navController)
        }
    }
}