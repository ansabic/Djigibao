package com.mite.djigibao.ui.edit_song

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mite.djigibao.database.entities.Song
import com.mite.djigibao.ui.DjigibaoUserDropDownItem
import com.mite.djigibao.ui.theme.Purple200
import com.mite.djigibao.ui.theme.Purple700

class EditSongScreen {
    @Composable
    fun EditSong(viewModel: EditSongViewModel, navController: NavController) {
        val song = navController.previousBackStackEntry?.arguments?.getParcelable<Song>("song")!!
        viewModel.initSong(song)
        val isPicking = viewModel.isPickingAuthor.observeAsState()
        val authors = viewModel.authors.observeAsState()
        val pickedAuthor = viewModel.pickedAuthor.observeAsState()
        val goBack = viewModel.goBack.observeAsState()
        val title = viewModel.title.observeAsState()
        val body = viewModel.body.observeAsState()
        viewModel.getAllAuthors()
        if (goBack.value == true)
            navController.navigateUp()
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
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
                TextField(
                    value = title.value ?: "",
                    onValueChange = {
                        viewModel.onTitleChange(it)
                    },
                    textStyle = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        color = Purple700
                    ),
                    modifier = Modifier
                        .wrapContentSize(),
                    label = {
                        if (title.value?.isEmpty() == true)
                            Text(
                                text = "Song title"
                            )
                    }
                )
                Spacer(
                    modifier = Modifier
                        .padding(
                            bottom = 20.dp
                        )
                )
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    if (isPicking.value == true) {
                        DropdownMenu(
                            modifier = Modifier
                                .wrapContentSize()
                                .background(color = Color.Transparent),
                            expanded = isPicking.value ?: false,
                            onDismissRequest = {

                            }) {
                            authors.value?.forEach {
                                DropdownMenuItem(
                                    onClick = {
                                        viewModel.selectAuthor(it)
                                        viewModel.pickAuthor(false)
                                    },
                                    modifier = Modifier
                                        .wrapContentHeight()
                                        .wrapContentWidth(),
                                ) {
                                    DjigibaoUserDropDownItem(it)
                                }
                            }
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)

                    ) {
                        Button(
                            onClick = {
                                viewModel.pickAuthor(true)
                            },
                            modifier = Modifier
                                .wrapContentHeight()
                        ) {
                            Text(
                                text = "Add author",
                                style = MaterialTheme.typography.body1,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .wrapContentSize()
                            )
                        }
                        Text(
                            text = pickedAuthor.value?.username ?: "",
                            Modifier
                                .wrapContentWidth(),
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.Center
                        )
                    }

                }
                Spacer(
                    modifier = Modifier
                        .padding(
                            bottom = 20.dp
                        )
                )
                TextField(
                    value = body.value ?: "",
                    onValueChange = {
                        viewModel.onBodyChange(it)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp, vertical = 40.dp)
                        .background(Color.Transparent),
                    textStyle = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        color = Purple700
                    )
                )
            }
            if (title.value?.isNotEmpty() == true && body.value?.isNotEmpty() == true && authors.value?.isNotEmpty() == true) {
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
                            painter = painterResource(R.drawable.ic_input_add),
                            colorFilter = ColorFilter.tint(Color.White),
                            contentDescription = ""
                        )

                    }
                }
            }
        }
    }
}