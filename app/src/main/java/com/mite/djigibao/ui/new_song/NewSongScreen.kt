package com.mite.djigibao.ui.new_song

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.mite.djigibao.ui.DjigibaoUserDropDownItem
import com.mite.djigibao.ui.theme.Purple200
import com.mite.djigibao.ui.theme.Purple700

class NewSongScreen {

    @Composable
    fun NewSong(viewModel: NewSongViewModel, navController: NavController) {
        val isPicking = viewModel.isPickingAuthor.observeAsState()
        val authors = viewModel.authors.observeAsState()
        val pickedAuthor = viewModel.pickedAuthor.observeAsState()
        val goBack = viewModel.goBack.observeAsState()
        var title by remember { mutableStateOf("") }
        var body by remember { mutableStateOf("") }
        viewModel.getAllAuthors()
        if(goBack.value == true)
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
                    value = title,
                    onValueChange = {
                        title = it
                        viewModel.onTitleChange(title)
                    },
                    textStyle = TextStyle(
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        color = Purple700
                    ),
                    modifier = Modifier
                        .wrapContentSize(),
                    label = {
                        if(title.isEmpty())
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
                   if(isPicking.value == true) {
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

                   ){
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
                           text = pickedAuthor.value?.username?:"",
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
                    value = body,
                    onValueChange = {
                        body = it
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
            if(title.isNotEmpty() && body.isNotEmpty() && authors.value?.isNotEmpty() == true){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 25.dp,bottom = 25.dp)
                ) {
                    FloatingActionButton(onClick = {
                        viewModel.addNewSong()
                    },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)){
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
}