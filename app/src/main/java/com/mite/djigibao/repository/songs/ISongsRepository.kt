package com.mite.djigibao.repository.songs

import com.mite.djigibao.database.entities.Song
import com.mite.djigibao.database.entities.User
import dagger.hilt.android.scopes.ViewModelScoped

@ViewModelScoped
interface ISongsRepository {
    suspend fun getAllSongs(): List<Song>
    suspend fun getSongByName(name: String): Song
    suspend fun deleteSong(name: String)
    suspend fun insertSong(song:Song)
    suspend fun insertSongs(songs: List<Song>)
    suspend fun getAllSongsOfAuthor(user: User):List<Song>
}