package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class NoteRequest(
    val note: Note,
   // val owner: User
)