package com.example.notesapp.presentation.notes

import com.example.notesapp.domain.model.Note
import com.example.notesapp.domain.model.repository.util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder) : NotesEvent()
    data class DeleteNote(val note: Note) : NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
}