package com.mite.djigibao.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class TodoItem(
    val text: String,
    val done: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}