package com.mite.djigibao.ui.edit_song

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mite.djigibao.database.entities.Song
import com.mite.djigibao.database.entities.User
import com.mite.djigibao.domain.firebase.FirestoreSongUseCase
import com.mite.djigibao.repository.songs.ISongsRepository
import com.mite.djigibao.repository.users.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class EditSongViewModel @Inject constructor(
    private val songRepository: ISongsRepository,
    private val userRepository: IUserRepository,
    private val firestoreSongUseCase: FirestoreSongUseCase
) : ViewModel() {
    private val _title by lazy { MutableLiveData<String>() }
    val title: LiveData<String> get() = _title
    private val _authors by lazy { MutableLiveData<List<User>>() }
    val authors: LiveData<List<User>> get() = _authors
    private val _body by lazy { MutableLiveData<String>() }
    val body: LiveData<String> get() = _body
    private val _isPickingAuthor by lazy { MutableLiveData(false) }
    val isPickingAuthor: LiveData<Boolean> get() = _isPickingAuthor
    private val _pickedAuthor by lazy { MutableLiveData<User>() }
    val pickedAuthor: LiveData<User> get() = _pickedAuthor
    private val _goBack by lazy { MutableLiveData<Boolean>() }
    val goBack: LiveData<Boolean> get() = _goBack

    fun onTitleChange(text: String) = viewModelScope.launch(Dispatchers.Default) {
        _title.postValue(text)
    }


    fun onBodyChange(text: String) = viewModelScope.launch(Dispatchers.Default) {
        _body.postValue(text)
    }

    fun addNewSong() = viewModelScope.launch(Dispatchers.IO) {
        pickedAuthor.value?.let {
            val resultSong = Song(
                name = _title.value ?: "",
                body = _body.value ?: "",
                user = it,
                creationDate = ZonedDateTime.now()
            )
            songRepository.insertSong(resultSong)
            firestoreSongUseCase.addSong(resultSong)
            _goBack.postValue(true)
        }

    }

    fun pickAuthor(flag: Boolean) = viewModelScope.launch {
        _isPickingAuthor.postValue(flag)
    }

    fun getAllAuthors() = viewModelScope.launch(Dispatchers.IO) {
        userRepository.getAllUsers()?.let {
            _authors.postValue(it)
        }
    }

    fun selectAuthor(author: User) = viewModelScope.launch(Dispatchers.Default) {
        _pickedAuthor.postValue(author)
    }

    fun initSong(song: Song) {
        _title.value = song.name
        _pickedAuthor.value = song.user
        _body.value = song.body
    }
}