package com.example.notesappwithcompose.Presentation

import androidx.lifecycle.ViewModel
import com.example.notesappwithcompose.Data.NoteDao

class NoteViewModel(
    private var dao: NoteDao
): ViewModel() {

}