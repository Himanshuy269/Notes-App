package com.example.notesapp.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.domain.model.Note
import com.example.notesapp.domain.model.repository.use_case.NoteUseCases
import com.example.notesapp.domain.model.repository.util.NoteOrder
import com.example.notesapp.domain.model.repository.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor (
    private val noteUseCases : NoteUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(NotesState())
    val state: StateFlow<NotesState> = _state
    private var recentlyDeleteNote: Note? =null
    private var getNotesJob : Job? =null
//In kotlin Coroutines, when you collect a Flow using .launchIn(viewModelScope), it returns a Job object.
//A Job represents a running background task that you can control (and most importantly, cancel).
    init{
        getNotes(NoteOrder.Date(OrderType.Descending))
    }    fun onEvent(event: NotesEvent){
        when(event){
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNotes(event.note)
                       // • event = The envelope object passed into the function onEvent(event: NotesEvent).
                   //.note = The property name inside DeleteNote(val note: Note).

                    recentlyDeleteNote=event.note
                }
            }
            is NotesEvent.Order -> {
                if(state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType){
                    return
                }
                getNotes(event.noteOrder)
            }
            NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNote(recentlyDeleteNote ?: return@launch)
                    recentlyDeleteNote = null
                }
            }
            NotesEvent.ToggleOrderSection -> {
//                _state.value = state.value.copy(
//                    isOrderSectionVisible = !state.value.isOrderSectionVisible
//                )
                _state.update{ it.copy (
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )}
            }
        }
    }
    private fun getNotes(noteOrder: NoteOrder){
            getNotesJob?.cancel()
        getNotesJob=noteUseCases.getNotes(noteOrder)
            .onEach { notes ->
                //_state.value=state.value.copy(
                    _state.update{it.copy(
                        notes = notes,
                        noteOrder= noteOrder
                    )
                    }
                   // notes = notes,
                    //noteOrder= noteOrder)
            }.launchIn(viewModelScope)
    }
}