package com.mite.djigibao.repository.calendar

import com.mite.djigibao.database.entities.Event
import dagger.hilt.android.scopes.ViewModelScoped

@ViewModelScoped
interface IEventRepository {
    suspend fun getAllEvents(): List<Event>
    suspend fun deleteEvent(id: Long)
    suspend fun insertEvent(event: Event)

}