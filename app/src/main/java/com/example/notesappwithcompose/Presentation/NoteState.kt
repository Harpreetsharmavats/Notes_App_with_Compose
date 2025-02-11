package com.example.notesappwithcompose.Presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.notesappwithcompose.Data.Note

class NoteState (
    var notes : List<Note> = emptyList(),
    val title : MutableState<String> = mutableStateOf(""),
    val description : MutableState<String> = mutableStateOf("")
)