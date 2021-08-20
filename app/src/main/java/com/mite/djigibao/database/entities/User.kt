package com.mite.djigibao.database.entities

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mite.djigibao.model.Role
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "users")
data class User(
    val username: String = "",
    val role: Role = Role.NONE,
    @PrimaryKey(autoGenerate = true)
    var userId: Long = 0L
) : Parcelable {

}