package com.mite.djigibao.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mite.djigibao.database.entities.User
import javax.inject.Singleton

@Dao
abstract class UserDao {
    @Query("SELECT * FROM users")
    abstract suspend fun getAllUsers(): List<User>

    @Insert(entity = User::class,onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertUser(user: User)

    @Insert(entity = User::class, onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertUsers(users: List<User>)

}