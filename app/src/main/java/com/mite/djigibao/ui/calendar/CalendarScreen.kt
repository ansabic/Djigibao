package com.mite.djigibao.ui.calendar

import android.widget.CalendarView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.mite.djigibao.database.entities.Event
import com.mite.djigibao.ui.theme.Purple200
import com.mite.djigibao.ui.theme.Purple500
import com.mite.djigibao.ui.theme.Purple700
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class CalendarScreen {

    @Composable
    fun Calendar(
        viewModel: CalendarViewModel,
        navController: NavController
    ) {


        viewModel.getAllEvents()
        val events = viewModel.events.observeAsState()
        val showDialog = viewModel.showDialog.observeAsState()
        Scaffold {
            Column(
                Modifier
                    .background(Purple200)
                    .fillMaxSize()
            ) {
                AndroidView(
                    factory = {
                        val calendar = CalendarView(it)
                        calendar.firstDayOfWeek = 2
                        calendar
                    },
                    update = {
                        it.setOnDateChangeListener { _, year, month, day ->
                            val pickedDate = ZonedDateTime.of(
                                year,
                                month + 1,
                                day,
                                0,
                                0,
                                0,
                                0,
                                ZoneId.of("Europe/Berlin")
                            )
                            viewModel.setDate(pickedDate)
                            viewModel.getAllEvents(pickedDate)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Button(
                    onClick = {
                        viewModel.showDialog(true)
                    },
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Add new event on picked date"
                    )
                }

                Text(
                    text = "Events",
                    fontSize = 20.sp,
                    color = Purple700,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 10.dp)
                )
                LazyColumn {
                    events.value?.forEach { event ->
                        item {
                            EventView(event)
                        }
                    }
                }

            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if (showDialog.value == true) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize(),
                        color = Color.Black.copy(alpha = 0.7f)
                    ) {
                        NewEventDialog(viewModel)
                    }

                }
            }


        }
    }

    @Preview
    @Composable
    fun EventView(
        event: Event = Event(
            title = "event title",
            description = "koncert tamo negdi",
            createdDate = ZonedDateTime.now().minusDays(2),
            dueDate = ZonedDateTime.now()
        )
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(vertical = 5.dp, horizontal = 5.dp)
                .border(
                    width = 1.dp,
                    color = Purple500
                )


        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Purple200)
                    .padding(bottom = 10.dp)
            ) {
                Text(
                    text = event.title,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    fontSize = 18.sp,
                    color = Purple500,
                )
                Row(
                    modifier = Modifier
                        .padding(
                            vertical = 10.dp,
                            horizontal = 10.dp
                        )
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Created at: ${
                            event.createdDate.format(
                                DateTimeFormatter.ofPattern("dd.MM.yyyy")
                            )
                        }",
                        fontSize = 12.sp,
                        color = Color.White,
                        modifier = Modifier
                    )
                    Text(
                        text = "Due date: ${
                            event.dueDate.format(
                                DateTimeFormatter.ofPattern("dd.MM.yyyy")
                            )
                        }",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
                Text(
                    text = event.description,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }
    }

    @Composable
    fun NewEventDialog(viewModel: CalendarViewModel) {
        val title = viewModel.title.observeAsState("")
        val description = viewModel.description.observeAsState("")
        Box(
            modifier = Modifier
                .wrapContentSize()
        ) {

            Column(
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                TextField(
                    value = title.value.toString(),
                    onValueChange = {
                        viewModel.updateTitle(it)
                    },
                    label = {
                        if (title.value.isEmpty())
                            Text(
                                text = "title",
                                color = Color.White
                            )
                    },
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(vertical = 10.dp)
                        .align(Alignment.CenterHorizontally)
                )
                TextField(
                    value = description.value,
                    onValueChange = {
                        viewModel.updateDescription(it)
                    },
                    label = {
                        if (description.value.isEmpty())
                            Text(
                                text = "description",
                                color = Color.White
                            )
                    },
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(vertical = 10.dp)
                        .align(Alignment.CenterHorizontally),
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    if (title.value.isNotEmpty() && description.value.isNotEmpty())
                        FloatingActionButton(
                            onClick = {
                                viewModel.newEvent()
                            },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(30.dp)
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

}