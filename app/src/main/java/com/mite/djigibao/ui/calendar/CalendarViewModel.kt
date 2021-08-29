package com.mite.djigibao.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mite.djigibao.database.entities.Event
import com.mite.djigibao.domain.firebase.FirestoreEventUseCase
import com.mite.djigibao.repository.calendar.IEventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val eventRepository: IEventRepository,
    private val firestoreEventUseCase: FirestoreEventUseCase
) : ViewModel() {

    private val _showDialog by lazy { MutableLiveData(false) }
    val showDialog: LiveData<Boolean> get() = _showDialog

    private val _events by lazy { MutableLiveData(emptyList<Event>()) }
    val events: LiveData<List<Event>> get() = _events

    private val date by lazy { MutableLiveData(ZonedDateTime.now()) }

    private val _title by lazy { MutableLiveData("") }
    val title: LiveData<String> get() = _title

    private val _description by lazy { MutableLiveData("") }
    val description: LiveData<String> get() = _description


    fun getAllEvents(promptDate: ZonedDateTime? = ZonedDateTime.now()) =
        viewModelScope.launch(Dispatchers.IO) {
            _events.postValue(
                eventRepository.getAllEvents()
                    .filter { it.dueDate.monthValue == promptDate?.monthValue && it.dueDate.year == promptDate.year }
                    .sortedBy { it.dueDate })
        }

    fun updateTitle(title: String) {
        _title.value = title
    }

    fun updateDescription(description: String) {
        _description.value = description
    }

    fun newEvent() = viewModelScope.launch(Dispatchers.IO) {
        val event = Event(
            title = title.value ?: "",
            description = description.value ?: "",
            createdDate = ZonedDateTime.now(),
            dueDate = date.value ?: ZonedDateTime.now()
        )
        eventRepository.insertEvent(event)
        firestoreEventUseCase.insertEventRemote(event)
        getAllEvents(date.value)
        _showDialog.postValue(false)
    }

    fun setDate(dateTime: ZonedDateTime?) {
        date.value = dateTime
    }

    fun showDialog(flag: Boolean) {
        _showDialog.value = flag
    }


}