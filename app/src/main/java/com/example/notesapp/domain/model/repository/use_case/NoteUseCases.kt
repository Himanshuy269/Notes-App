package com.example.notesapp.domain.model.repository.use_case

data class NoteUseCases(
    val getNotes : GetNotes,
    val deleteNotes : DeleteNote,
    val addNote: AddNote
)