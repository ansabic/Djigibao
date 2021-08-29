package com.mite.djigibao.repository.calendar

import com.mite.djigibao.database.daos.EventDao
import com.mite.djigibao.database.entities.Event
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val eventDao: EventDao
) : IEventRepository {
    override suspend fun getAllEvents(): List<Event> {
        return eventDao.getAllEvents()
    }

    override suspend fun deleteEvent(id: Long) {
        eventDao.deleteEvent(id)
    }

    override suspend fun insertEvent(event: Event) {
        eventDao.insertEvent(event)
    }

    override suspend fun insertEvents(events: List<Event>) {
        eventDao.insertEvents(events)
    }
}