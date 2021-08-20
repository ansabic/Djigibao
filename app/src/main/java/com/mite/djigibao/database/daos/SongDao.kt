package com.mite.djigibao.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mite.djigibao.database.entities.Song
import com.mite.djigibao.database.entities.User

@Dao
abstract class SongDao {
    @Query("SELECT * FROM songs")
    abstract suspend fun getAllSongs(): List<Song>

    @Query("SELECT * FROM songs WHERE username = :name")
    abstract suspend fun getSongByName(name: String): Song

    @Query("DELETE FROM songs WHERE username = :name")
    abstract suspend fun deleteByName(name: String)

    @Insert(entity = Song::class, onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertSong(song: Song)

    @Query("SELECT * FROM songs WHERE :username == username")
    abstract suspend fun getAllFromAuthor(username:String): List<Song>

    @Insert(entity = Song::class, onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertSongs(songs: List<Song>)
}