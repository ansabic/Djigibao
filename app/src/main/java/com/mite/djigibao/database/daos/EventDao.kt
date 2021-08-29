package com.mite.djigibao.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mite.djigibao.database.entities.Event

@Dao
abstract class EventDao {

    @Query("SELECT * FROM events")
    abstract suspend fun getAllEvents(): List<Event>

    @Insert(entity = Event::class, onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertEvent(event: Event)

    @Query("DELETE FROM events WHERE id == :id")
    abstract suspend fun deleteEvent(id: Long)

    @Insert(entity = Event::class, onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertEvents(events: List<Event>)

}