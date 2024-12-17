package com.example.model

class NoteRepository {
    private val notes: MutableList<Note> = mutableListOf()

    fun getNotes(): List<Note> = notes

    fun getNote(id: Int): Note? {
        notes.forEach { note ->
            if (note.id == id) {
                return note
            }
        }
        return null
    }

    fun addNote(createdNote: NoteRequest): Note? {
        getNote(createdNote.note.id)?.let {
            return null
        }
        notes.add(createdNote.note)
        return createdNote.note
    }


    fun deleteNote(deletedNote: NoteRequest): NoteResponse {
        val note = notes.find { note ->
            note.id == deletedNote.note.id
        }
        if (note != null) {
            notes.remove(note)
            return NoteResponse.OnSuccess(note = note)
        }
        return NoteResponse.OnFailure(errMsg = "Note with ID ${deletedNote.note.id} not found.")
    }

    fun updateNote(newNote: NoteRequest) :NoteResponse {
        val index = notes.indexOfFirst { note -> note.id == newNote.note.id }
        if (index != -1) {
            notes[index] = notes[index].copy(title = newNote.note.title, description = newNote.note.description)
            return NoteResponse.OnSuccess(notes[index])
        }
        return NoteResponse.OnFailure("Note with ID ${newNote.note.id} not found.")
    }
}