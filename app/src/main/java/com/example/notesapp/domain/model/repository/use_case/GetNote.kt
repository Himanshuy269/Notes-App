package com.example.notesapp.domain.model.repository.use_case

import com.example.notesapp.domain.model.Note
import com.example.notesapp.domain.model.repository.NoteRepository

class GetNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int): Note?{
        return repository.getNoteById(id)
    }
}