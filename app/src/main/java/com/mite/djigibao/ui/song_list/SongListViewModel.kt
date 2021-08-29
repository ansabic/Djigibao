package com.mite.djigibao.ui.song_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mite.djigibao.database.entities.Song
import com.mite.djigibao.repository.songs.ISongsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongListViewModel @Inject constructor(
    private val songRepository: ISongsRepository
) : ViewModel() {
    private val _goToNewSong by lazy { MutableLiveData<Boolean>() }
    val goToNewSong: LiveData<Boolean> get() = _goToNewSong

    private val _songs by lazy { MutableLiveData<List<Song>>() }
    val songs: LiveData<List<Song>> get() = _songs

    fun addNewSong() = viewModelScope.launch(Dispatchers.IO) {
        _goToNewSong.postValue(true)
    }

    fun leftScreen() = viewModelScope.launch(Dispatchers.IO) {
        _goToNewSong.postValue(false)
    }

    fun getSongs() = viewModelScope.launch(Dispatchers.IO) {
        _songs.postValue(
            songRepository.getAllSongs()
        )
    }

}