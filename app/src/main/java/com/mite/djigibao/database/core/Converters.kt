package com.mite.djigibao.database.core

import androidx.room.TypeConverter
import com.mite.djigibao.core.OFFSET_DATE_TIME_PATTERN
import com.mite.djigibao.model.Instrument
import com.mite.djigibao.model.Role
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class Converters {
    private val dateTimeFormatter = DateTimeFormatter.ofPattern(OFFSET_DATE_TIME_PATTERN).withZone(
        ZoneId.of("Europe/Berlin"))


    @TypeConverter
    fun listOfInstrumentsToString(instruments: List<Instrument>) = instruments.joinToString("/")


    @TypeConverter
    fun stringToListOfInstruments(string: String): List<Instrument> =
        string.split("/")
            .map { text ->
                Instrument.values().find { it.text == text } ?: Instrument.NO_INSTRUMENT
            }

    @TypeConverter
    fun offsetDateTimeToString(time: ZonedDateTime) =
        dateTimeFormatter.format(time)

    @TypeConverter
    fun stringToOffsetDateTime(string: String): ZonedDateTime =
        ZonedDateTime.parse(string,dateTimeFormatter)

}