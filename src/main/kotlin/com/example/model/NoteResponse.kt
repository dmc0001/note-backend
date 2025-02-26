package com.example.model

sealed class NoteResponse {
    data class OnSuccess(val note: Note) : NoteResponse()
    class OnFailure(val errMsg: String?) : NoteResponse()
}
