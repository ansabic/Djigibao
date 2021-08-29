package com.mite.djigibao.domain.firebase.entities

import com.google.gson.annotations.SerializedName

data class FirestoreEvent(
    @SerializedName("title")
    val title: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("created_date")
    val createdDate: String = "",
    @SerializedName("due_date")
    val dueDate: String = ""
) {
}