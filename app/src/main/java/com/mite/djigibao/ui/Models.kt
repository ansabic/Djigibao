package com.mite.djigibao.ui

import android.widget.Space
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mite.djigibao.R
import com.mite.djigibao.database.entities.Song
import com.mite.djigibao.database.entities.TodoItem
import com.mite.djigibao.database.entities.User
import com.mite.djigibao.model.Role
import com.mite.djigibao.ui.theme.Purple500
import com.mite.djigibao.ui.theme.Purple700
import com.mite.djigibao.ui.theme.Shapes
import java.time.ZonedDateTime

sealed class Destination(val name: String) {
    object Login : Destination("login")
    object MainScreen : Destination("main_screen")
    object SongList : Destination("song_list")
    object NewSong : Destination("new_song")
    object Todo : Destination("todo")
}

@Preview
@Composable
fun NewTodoItem(newItem: (TodoItem) -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        var text by remember { mutableStateOf("") }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Spacer(
                modifier = Modifier
                    .padding(start = 10.dp)
            )

            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth(0.9f)
                    .padding(start = 30.dp),
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    color = Purple700
                )
            )
            Spacer(
                modifier = Modifier
                    .padding(start = 15.dp)
            )
            Image(
                painter = painterResource(R.drawable.tick),
                colorFilter = ColorFilter.tint(Color.Green),
                contentDescription = "",
                modifier = Modifier
                    .size(width = 25.dp,height = 25.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        if(text.isNotEmpty())
                            newItem(TodoItem(
                                text = text,
                                done = false
                            ))
                    }
            )
        }
    }
}

@Preview
@Composable
fun TodoItem(
    resolved: Boolean = false,
    text: String = "test tasfwfsdf text",
    onCheck: (Boolean) -> Unit = {},
    deleteTodo: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 5.dp,horizontal = 10.dp)
    ) {
        var done by remember { mutableStateOf(resolved) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    shape = Shapes.medium,
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Purple500,
                            Purple700
                        )
                    )
                )
                .padding(5.dp)
            ,
        ) {
            Spacer(
                modifier = Modifier
                    .padding(start = 30.dp)
            )
            Image(
                painter = painterResource(R.drawable.tick),
                colorFilter = ColorFilter.tint(Color.Green),
                contentDescription = "",
                modifier = Modifier
                    .size(width = 25.dp, height = 25.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        done = !done
                        onCheck(done)
                    },
            )
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Purple700,
                    textDecoration =
                    if (done)
                        TextDecoration.LineThrough
                    else
                        TextDecoration.None

                ),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth(0.8f)
                    .padding(start = 30.dp)
            )
            Image(
                painter = painterResource(android.R.drawable.ic_menu_close_clear_cancel),
                colorFilter = ColorFilter.tint(Color.Red),
                contentDescription = "",
                modifier = Modifier
                    .size(width = 25.dp,height = 25.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        deleteTodo()
                    }
            )
        }
    }
}



@Preview
@Composable
fun SongItemStretch(
    song: Song = Song(
        name = "test name",
        user =
            User("Mate", Role.VOCAL)
        ,
        body = "this is a \n body \n of a \n test song",
        creationDate = ZonedDateTime.now()
    ), stretched: Boolean = true, click: () -> Unit = { }
) =
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                click()
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .border(
                    width = 2.dp,
                    shape = Shapes.medium,
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Purple500,
                            Purple700
                        )
                    )
                )
        ) {
            Spacer(modifier = Modifier
                .padding(
                    vertical = 5.dp
                ))
            Text(
                text  = song.name.split(" ").joinToString(" ") {
                    it.lowercase().replaceFirstChar { char -> char.titlecase() }
                },
                style = TextStyle(
                    fontSize = 24.sp,
                    color = Purple700

                )
            )
            if (stretched) {
                Column {
                    Text(
                        text = song.user?.username?:"",
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(
                                end = 18.dp
                            ),
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Purple700
                        )
                    )
                    Spacer(
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                    )
                    Text(
                        text = song.body,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 14.sp
                        )

                    )
                }
            }
            Spacer(modifier = Modifier
                .padding(
                    vertical = 5.dp
                ))
        }
    }



@Composable
fun DjigibaoTitle(title: String = "Djigibao Manager") =
    Text(
        text = title,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h4,
        modifier = Modifier
            .padding(10.dp),
        fontStyle = FontStyle.Italic
    )

@Composable
fun SimpleText(title: String = "") =
    Text(
        text = title,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(top = 8.dp),
        color = Purple700
    )

@Composable
fun DjigibaoSettingItem(title: String, action:() -> Unit) {
    Button(
        action,
        modifier = Modifier
            .padding(16.dp)
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Purple700, Shapes.small)
                .padding(16.dp)
        )

    }
}

@Composable
fun UsernameInput(
    onTextChange: (String) -> Unit = {}
) {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = {
            text = it
            onTextChange(it)
        },
        label = {
            Text(
                text = "Username",
                color = Purple700
            )
        },
        modifier = Modifier
            .width(300.dp)
    )
}

@Composable
fun PasswordInput(onTextChange: (String) -> Unit = {}) {
    var password by remember { mutableStateOf("") }
    TextField(
        value = password,
        onValueChange = {
            password = it
            onTextChange(it)
        },
        label = {
            Text(
                text = "Password",
                color = Purple700
                )
        },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier
            .width(300.dp)
    )
}

@Composable
fun DjigibaoButtonWrap(text:String, visible: Boolean = true, action:() -> Unit) {
    if(visible) {
        Button(
            onClick = action,
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
            )
        }
    }
}
@Composable
fun DjigibaoButtonStretch(text:String, visible: Boolean = true, action:() -> Unit) {
    if(visible) {
        Button(
            onClick = action,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
            )
        }
    }
}

@Composable
fun DjigibaoDropDownItem(
    role: Role = Role.NONE
) {
    Text(
        text = role.text,
        color = Color.White,
        textAlign = TextAlign.Center,
        fontSize = 14.sp,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    )
}

@Composable
fun DjigibaoUserDropDownItem(
    user: User
) {
    Text(
        text = user.username,
        color = Color.White,
        textAlign = TextAlign.Center,
        fontSize = 14.sp,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    )
}
@Preview
@Composable
fun DjigibaoRoleDropdown(
    isPicking: Boolean = false,
    list: List<Role> = Role.values().toList(),
    selectedItem: (Role) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    DropdownMenu(
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()
            .background(color = Color.Transparent),
        expanded = isPicking,
        onDismissRequest = {

        }) {
        list.forEach {
            DropdownMenuItem(
                onClick = {
                    selectedItem.invoke(it)
                    onDismiss.invoke()
                },
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth(),
            ) {
                DjigibaoDropDownItem(it)
            }
        }
    }

}

