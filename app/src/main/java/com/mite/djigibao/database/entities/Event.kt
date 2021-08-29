package com.mite.djigibao.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.ZonedDateTime

@Entity(tableName = "events")
data class Event(
    val title: String,
    val description: String,
    val createdDate: ZonedDateTime,
    val dueDate: ZonedDateTime
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
}