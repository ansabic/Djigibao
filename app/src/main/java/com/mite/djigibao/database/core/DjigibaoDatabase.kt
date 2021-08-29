package com.mite.djigibao.database.core

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mite.djigibao.database.daos.EventDao
import com.mite.djigibao.database.daos.SongDao
import com.mite.djigibao.database.daos.TodoDao
import com.mite.djigibao.database.daos.UserDao
import com.mite.djigibao.database.entities.Event
import com.mite.djigibao.database.entities.Song
import com.mite.djigibao.database.entities.TodoItem
import com.mite.djigibao.database.entities.User
import javax.inject.Singleton

@Database(
    entities = [User::class, Song::class, TodoItem::class, Event::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
@Singleton
abstract class DjigibaoDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun songDao(): SongDao
    abstract fun todoDao(): TodoDao
    abstract fun eventDao(): EventDao
}