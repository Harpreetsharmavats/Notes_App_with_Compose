package com.example.notesappwithcompose.Presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import java.lang.reflect.Modifier

@Composable
fun AddNoteScreen(
    state: NoteState,
    modifier: Modifier,
    navController: NavController,
    onEvent: (NotesEvent) ->Unit
) {
    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(
                    NotesEvent.SaveNote(
                        title = state.title.value,
                        description = state.description.value
                    )
                )
                navController.popBackStack()
            }) {
                Icon(imageVector = Icons.Rounded.Check,contentDescription = null)
            }
        }
    )

}

fun Scaffold(floatingActionButton: @Composable () -> Unit) {
    TODO("Not yet implemented")
}
