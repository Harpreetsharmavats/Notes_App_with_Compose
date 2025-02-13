package com.example.notesappwithcompose.Presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesappwithcompose.Data.Note
import com.example.notesappwithcompose.Data.NoteDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteViewModel(
    private var dao: NoteDao
): ViewModel() {
private var isSortedByDateAdded = MutableStateFlow(true)

    private var notes = isSortedByDateAdded.flatMapLatest {
        if (it){
            dao.getOrderedByDateAdded()
        }else{
            dao.getOrderedByTitle()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    var _state = MutableStateFlow(NoteState())
    var state = combine(_state, isSortedByDateAdded, notes){
        state , isSortedByDateAdded , notes ->
        state.copy(
            notes = notes,
            title = mutableStateOf(""),
            description = mutableStateOf("")
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),NoteState())

    fun onEvent(event: NotesEvent){
        when(event){
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    dao.delete(event.note)
                }
            }
            is NotesEvent.SaveNote -> {
                val note = Note(
                    title = state.value.title.value,
                    description = state.value.description.value,
                    dateAdded = System.currentTimeMillis()
                )
                viewModelScope.launch {
                    dao.upsertNote(note = note)

                }
                _state.update{
                    it.copy(
                        title = mutableStateOf(""),
                        description = mutableStateOf("")
                    )
                }
            }
            NotesEvent.SortNotes -> {
isSortedByDateAdded.value = ! isSortedByDateAdded.value
            }
        }
    }

}