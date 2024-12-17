package com.example

import com.example.model.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureRouting() {

    val repo = NoteRepository()
    routing {

        get("/") {
            call.respondText("Hello Ktor!")
        }

        respondOr405(
            path = "/api/v1/notes/list",
            block = {
                get {
                    val notes = repo.getNotes()
                    if (notes.isNotEmpty()) {
                        call.respond(HttpStatusCode.OK, notes)
                    } else {
                        call.respond(HttpStatusCode.NoContent)
                    }
                }
            }
        )

        respondOr405(path = "/api/v1/notes/find", block = {
            get {
                call.parameters["id"]?.toIntOrNull()?.let { id ->
                    val note = repo.getNote(id = id)
                    if (note != null) {
                        call.respond(HttpStatusCode.OK, note)
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
                } ?: run {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
        })

        respondOr405(
            path = "/api/v1/notes/create",
            block = {
                post {
                    val note = call.receive<NoteRequest>()
                    if (repo.addNote(createdNote = note) != null)
                        call.respond(HttpStatusCode.Created, "Added new note ${note.note} successfully!")
                    else
                        call.respond(HttpStatusCode.Conflict, "Note already exists")
                }

            }

        )

        respondOr405("/api/v1/notes/update", block = {
            put {
                call.receive<NoteRequest>().let { updatedNote ->

                    when (val result = repo.updateNote(newNote = updatedNote)) {
                        is NoteResponse.OnFailure -> {
                            result.errMsg?.let { err ->
                                call.respond(HttpStatusCode.NotFound, err)
                            }
                        }

                        is NoteResponse.OnSuccess -> {
                            call.respond(
                                HttpStatusCode.OK,
                                "Note :${result.note.title} successfully updated!"
                            )
                        }
                    }

                }

            }
        })

        respondOr405(
            path = "/api/v1/notes/delete",
            block = {
                delete {
                    call.receive<NoteRequest>().let { deletedNote ->
                        when (val result = repo.deleteNote(deletedNote = deletedNote)) {

                            is NoteResponse.OnSuccess -> {
                                call.respond(
                                    HttpStatusCode.OK,
                                    "Note :${result.note.title} successfully deleted!"
                                )
                            }

                            is NoteResponse.OnFailure -> {
                                result.errMsg?.let { err ->
                                    call.respond(HttpStatusCode.NotFound, err)
                                }
                            }

                        }
                    } ?: run {
                        call.respond(HttpStatusCode.BadRequest, "Invalid or missing note ID.")
                    }
                }
            }

        )

        staticResources("/static", "static")
    }


}

private fun Route.respondOr405(path: String, block: Route.() -> Unit) {
    route(path) {
        block()
        handle {
            call.respond(HttpStatusCode.MethodNotAllowed, "Method not allowed")
        }
    }
}