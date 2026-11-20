package com.example.notesapp.presentation.add_edit_note

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.notesapp.domain.model.Note
import com.example.notesapp.domain.model.repository.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.notesapp.domain.model.InvalidNoteException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _noteTitle = MutableStateFlow(NoteTextFieldState(hint="Enter Title..."))
    val noteTitle: StateFlow<NoteTextFieldState> = _noteTitle

    private val _noteContent = MutableStateFlow(NoteTextFieldState(hint ="Enter Some content"))
    val noteContent: StateFlow<NoteTextFieldState> = _noteContent

    private val _noteColor = MutableStateFlow(Note.noteColors.random().toArgb())
    val noteColor: StateFlow<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()
    private var currentNoteId: Int? = null
    init {
        savedStateHandle.get<Int> ("noteId") ?.let { noteId ->
            if(noteId != -1){
                viewModelScope.launch{
                    noteUseCases.getNote(noteId)?.also{note ->
                        currentNoteId = note.id
                        _noteTitle.update{it.copy(
                            text=note.title,
                            isHintVisible = false
                        )}
                        _noteContent.update{it.copy(
                            text=note.content,
                            isHintVisible = false
                        )}
                        _noteColor.value = note.color
                    }
                }
        }
        }
    }
    fun onEvent(event: AddEditNoteEvent) {
        when(event){
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.update{it.copy(
                    isHintVisible = !event.focusState.isFocused &&
                    noteTitle.value.text.isBlank()
                )}
            }
            is AddEditNoteEvent.ChangedContentFocus -> {
                _noteContent.update{it.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            noteContent.value.text.isBlank()
                )
                }
            }
            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.update {
                    it.copy(
                        text = event.value
                    )
                }
            }
            is AddEditNoteEvent.EnteredTitle -> {
//                _noteTitle.value=noteTitle.value.copy(
//                    text = event.value
//                )
                _noteTitle.update{it.copy(
                    text = event.value
                )}
            }
            AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                    noteUseCases.addNote(
                        Note(
                            title = noteTitle.value.text,
                            content =noteContent.value.text,
                            timestamp = System.currentTimeMillis(),
                            color = noteColor.value,
                            id = currentNoteId
                        )
                    )
                        _eventFlow.emit(UiEvent.SaveNote)
                }catch (e: InvalidNoteException){
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            message = e.message ?: "Couldn't save note"
                        )
                    )
                }
                }
            }
            is AddEditNoteEvent.ChangeColor -> {
                    _noteColor.value = event.color
                }
            }
        }
    //val noteColor: StateFlow<Int> = _noteColor
    sealed class UiEvent{
        data class ShowSnackbar (val message: String): UiEvent()
        object SaveNote: UiEvent()
    }
}