package com.example.notesapp.domain.model.repository.use_case

import com.example.notesapp.domain.model.Note
import com.example.notesapp.domain.model.repository.NoteRepository
import com.example.notesapp.domain.model.repository.util.NoteOrder
import com.example.notesapp.domain.model.repository.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes (
    private val repository : NoteRepository
) {
    operator fun invoke(
        noteOrder: NoteOrder= NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when (noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.Color -> notes.sortedBy { it.color }
                        is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                    }
                }

                is OrderType.Descending -> {
                    when (noteOrder) {
                        is NoteOrder.Color -> notes.sortedByDescending { it.color }
                        is NoteOrder.Date -> notes.sortedByDescending { it.timestamp }
                        is NoteOrder.Title -> notes.sortedByDescending {
                            it.title.lowercase()
                        }
                    }
                }
            }
        }
    }
}