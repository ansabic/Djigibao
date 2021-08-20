package com.mite.djigibao.repository.songs

import com.mite.djigibao.database.daos.SongDao
import com.mite.djigibao.database.entities.Song
import com.mite.djigibao.database.entities.User
import javax.inject.Inject

class SongsRepositoryImpl @Inject constructor(
    private val songDao: SongDao
): ISongsRepository {
    override suspend fun getAllSongs(): List<Song> {
        return songDao.getAllSongs()
    }

    override suspend fun getSongByName(name: String): Song {
        return songDao.getSongByName(name)
    }

    override suspend fun deleteSong(name: String) {
        songDao.deleteByName(name)
    }

    override suspend fun insertSong(song: Song) {
        songDao.insertSong(song)
    }

    override suspend fun insertSongs(songs: List<Song>) {
        songDao.insertSongs(songs)
    }

    override suspend fun getAllSongsOfAuthor(user: User): List<Song> {
        return songDao.getAllFromAuthor(user.username)
    }
}