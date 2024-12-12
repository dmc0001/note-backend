package com.example.model

object NoteRepository {
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

    fun addNote(note: Note) {
        notes.add(note)
    }

    fun deleteNote(id: Int) {
        val note = notes.find { it.id == id }
        if (note != null) {
            notes.remove(note)
        }
    }

    fun update(id: Int, newNote: Note) {
        val index = notes.indexOfFirst { it.id == id }
        if (index != -1) {
            notes[index] = newNote
        }
    }
}