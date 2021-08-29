package com.mite.djigibao.domain.firebase.entities

import com.google.gson.annotations.SerializedName
import com.mite.djigibao.model.Instrument

data class FirestoreSong(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("body")
    val body: String = "",
    @SerializedName("duration")
    val duration: Long = 0L,
    @SerializedName("creationDate")
    val creationDate: String = "",
    @SerializedName("instruments")
    val instruments: List<Instrument> = emptyList(),
    @SerializedName("user")
    val user: String = "",
    @SerializedName("songLocationLocal")
    val songLocationLocal: String? = null,
    @SerializedName("songLocationRemote")
    val songLocationRemote: String? = null,
)