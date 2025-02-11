package com.example.notesappwithcompose.Presentation

import com.example.notesappwithcompose.Data.Note

sealed interface NotesEvent {
    object SortNotes: NotesEvent
    data class DeleteNote(var note: Note):NotesEvent
    data class SaveNote(var title : String, var description : String):NotesEvent
}