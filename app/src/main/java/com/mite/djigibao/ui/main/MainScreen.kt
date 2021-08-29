package com.mite.djigibao.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mite.djigibao.Destination
import com.mite.djigibao.ui.DjigibaoSettingItem
import com.mite.djigibao.ui.DjigibaoTitle
import com.mite.djigibao.ui.theme.Purple200
import javax.inject.Inject

class MainScreen @Inject constructor() {

    @Composable
    fun MainScreen(viewModel: MainViewModel, navController: NavHostController) {
        Scaffold {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Purple200),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    DjigibaoTitle()
                    Spacer(
                        modifier = Modifier
                            .padding(top = 50.dp)
                    )
                    DjigibaoSettingItem("Songlist") {
                        navController.navigate(Destination.SongList.name)
                    }

                    DjigibaoSettingItem("Calendar") {
                        navController.navigate(Destination.Calendar.name)
                    }
                    DjigibaoSettingItem("Todo") {
                        navController.navigate(Destination.Todo.name)

                    }
                    DjigibaoSettingItem("Quick Rec") {

                    }

                }
            }
        }
    }
}