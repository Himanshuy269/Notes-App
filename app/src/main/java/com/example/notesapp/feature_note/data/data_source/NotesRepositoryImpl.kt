package com.example.notesapp.feature_note.data.data_source

import com.example.notesapp.domain.model.Note
import com.example.notesapp.domain.model.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NotesRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {
    override suspend fun deleteNote(note: Note){
        dao.deleteNode(note)
    }

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }
}