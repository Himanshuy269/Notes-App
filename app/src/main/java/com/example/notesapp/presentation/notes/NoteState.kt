package com.example.notesapp.presentation.notes

import com.example.notesapp.domain.model.Note
import com.example.notesapp.domain.model.repository.util.NoteOrder
import com.example.notesapp.domain.model.repository.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,

)
