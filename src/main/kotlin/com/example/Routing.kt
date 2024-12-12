package com.example

import com.example.model.Note
import com.example.model.NoteRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val repo = NoteRepository
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        route("/notes/{id}") {
            get {
                val id = call.parameters["id"]?.toIntOrNull()
                id?.let {
                    val note = repo.getNote(id = id)
                    note?.let {
                        call.respond(HttpStatusCode.OK, it)
                    } ?: run {
                        call.respond(HttpStatusCode.NotFound)
                    }
                } ?: run {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
            handle {
                call.respond(HttpStatusCode.MethodNotAllowed, "Method not allowed")
            }

        }

        route("/notes") {
            get {
                val notes = repo.getNotes()
                if (notes.isNotEmpty()) {
                    call.respond(HttpStatusCode.OK, notes)
                } else {
                    call.respond(HttpStatusCode.NoContent)
                }
            }
            handle {
                call.respond(HttpStatusCode.MethodNotAllowed, "Method not allowed")
            }

        }

        route("/notes/create-note") {
            post {
                val note = call.receive<Note>()
                call.application.environment.log.info("Note created: $note")
                repo.addNote(note = note)
                call.respond(HttpStatusCode.Created, "Added new note ${note.id} successfully!")
            }
            handle {
                call.respond(HttpStatusCode.MethodNotAllowed, "Method not allowed")
            }

        }

        route("/notes/delete-note") {
            delete {
                val id = call.parameters["id"]?.toIntOrNull()
                call.application.environment.log.info("Note deletion attempt: $id")
                id?.let {
                    val note = repo.getNote(id = id)
                    if (note != null) {
                        repo.deleteNote(id = id)
                        call.respond(HttpStatusCode.OK, "Deleted note with ID $id successfully!")
                    } else {
                        call.respond(HttpStatusCode.NotFound, "Note with ID $id not found.")
                    }
                } ?: run {
                    call.respond(HttpStatusCode.BadRequest, "Invalid or missing note ID.")
                }
            }
            handle {
                call.respond(HttpStatusCode.MethodNotAllowed, "Method not allowed")
            }
        }


        route("/notes/update-note") {
            put {
                val id = call.parameters["id"]?.toIntOrNull()
                val newNote = call.receive<Note>()
                id?.let {
                    repo.update(id = id, newNote = newNote)
                    call.respond(HttpStatusCode.OK, "Updated note $id successfully!")
                }
            }
            handle {
                call.respond(HttpStatusCode.MethodNotAllowed, "Method not allowed")
            }

        }
        // Static plugin. Try to access `/static/index.html`
        staticResources("/static", "static")
    }
}
