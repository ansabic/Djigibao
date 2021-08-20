package com.mite.djigibao.database.entities

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mite.djigibao.model.Instrument
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZonedDateTime

@Entity(tableName = "songs")
@Parcelize
data class Song(
    val name: String= "unnamed",
    val body: String = "",
    val duration: Long = 0L,
    val creationDate: ZonedDateTime,
    val instruments: List<Instrument> = emptyList(),
    @Embedded
    val user: User,
    val songLocationLocal: String? = null,
    val songLocationRemote: String? = null,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
) : Parcelable {
}