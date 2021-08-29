package com.mite.djigibao.ui.lista_pisama

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mite.djigibao.Destination
import com.mite.djigibao.ui.SongItemStretch
import com.mite.djigibao.ui.theme.Purple200
import javax.inject.Inject

class SongListScreen @Inject constructor() {

    @Composable
    fun SongList(viewModel: SongListViewModel, navController: NavController) {
        viewModel.getSongs()
        val goToNewSong = viewModel.goToNewSong.observeAsState(false)
        if (goToNewSong.value) {
            navController.navigate(Destination.NewSong.name)
            viewModel.leftScreen()
        }
        val songs = viewModel.songs.observeAsState()
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Purple200)
            ) {
                Spacer(
                    modifier = Modifier
                        .padding(
                            bottom = 20.dp
                        )
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp)

                ) {
                    songs.value?.forEach {
                        item {
                            var stretchState by remember { mutableStateOf(false) }
                            SongItemStretch(
                                song = it,
                                stretched = stretchState
                            ) {
                                stretchState = !stretchState
                            }
                            Spacer(
                                modifier = Modifier
                                    .padding(
                                        bottom = 4.dp
                                    )
                            )
                        }

                    }

                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 25.dp, bottom = 25.dp)
            ) {
                FloatingActionButton(
                    onClick = {
                        viewModel.addNewSong()
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