package com.mite.djigibao.domain.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.mite.djigibao.core.FIRESTORE_EVENTS
import com.mite.djigibao.core.OFFSET_DATE_TIME_PATTERN
import com.mite.djigibao.database.entities.Event
import com.mite.djigibao.domain.firebase.entities.FirestoreEvent
import com.mite.djigibao.repository.calendar.IEventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.coroutines.resume

class FirestoreEventUseCase @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val eventRepository: IEventRepository
) {
    private suspend fun getEventsRemote() = withContext(Dispatchers.IO) {
        val localEvents = eventRepository.getAllEvents()
        return@withContext suspendCancellableCoroutine<List<Event>> { continuation ->
            firestore.collection(FIRESTORE_EVENTS)
                .get()
                .addOnSuccessListener {
                    val data = it.documents
                    val resultList = mutableListOf<Event>()
                    data.forEach { document ->
                        val fireEvent = document.toObject(FirestoreEvent::class.java)
                        if (!localEvents.map { localEvent -> localEvent.title }
                                .contains(fireEvent?.title)) {
                            fireEvent?.let { firestoreEvent ->
                                mapFirestoreEventToEvent(firestoreEvent)
                            }?.let { event ->
                                resultList.add(event)
                            }
                        }
                    }
                    continuation.resume(resultList.toList())
                }
                .addOnFailureListener {
                    continuation.cancel()
                }
                .addOnCanceledListener {
                    continuation.cancel()
                }
        }

    }

    suspend fun syncEvents() {
        val local = eventRepository.getAllEvents()
        val remote = getEventsRemote()
        val resultList = mutableListOf<Event>()
        remote.forEach {
            if (!local.map { event -> event.title }.contains(it.title))
                resultList.add(it)
        }
        eventRepository.insertEvents(resultList.toList())
    }

    suspend fun insertEventRemote(event: Event) = withContext(Dispatchers.IO) {
        val remote = getEventsRemote()
        return@withContext if (!remote.map { it.title }.contains(event.title))
            suspendCancellableCoroutine { continuation ->
                firestore.collection(FIRESTORE_EVENTS)
                    .document(event.title)
                    .set(
                        mapEventToFirestoreEvent(event)
                    )
                    .addOnSuccessListener {
                        continuation.resume(true)
                    }
                    .addOnCanceledListener {
                        continuation.resume(false)
                    }
                    .addOnFailureListener {
                        continuation.resume(false)
                    }
            }
        else
            false
    }

    private fun mapEventToFirestoreEvent(event: Event): FirestoreEvent {
        val dateTimeFormatter = DateTimeFormatter.ofPattern(OFFSET_DATE_TIME_PATTERN).withZone(
            ZoneId.of("Europe/Berlin")
        )
        return FirestoreEvent(
            title = event.title,
            description = event.description,
            createdDate = event.createdDate.format(dateTimeFormatter),
            dueDate = event.dueDate.format(dateTimeFormatter)
        )
    }

    private fun mapFirestoreEventToEvent(fireEvent: FirestoreEvent): Event {
        val dateTimeFormatter = DateTimeFormatter.ofPattern(OFFSET_DATE_TIME_PATTERN).withZone(
            ZoneId.of("Europe/Berlin")
        )
        return Event(
            title = fireEvent.title,
            description = fireEvent.description,
            createdDate = ZonedDateTime.parse(fireEvent.createdDate, dateTimeFormatter),
            dueDate = ZonedDateTime.parse(fireEvent.dueDate, dateTimeFormatter)
        )
    }

}