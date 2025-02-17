package com.example.notesappwithcompose


import AddNotesScreen
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.notesappwithcompose.Data.NoteDatabase
import com.example.notesappwithcompose.Presentation.NoteScreen
import com.example.notesappwithcompose.Presentation.NoteViewModel
import com.example.notesappwithcompose.ui.theme.NotesAppWithComposeTheme


class MainActivity : ComponentActivity() {
    private lateinit var database: NoteDatabase
    private lateinit var viewModel: NoteViewModel

    /*private val viewModel by viewModels<NoteViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NoteViewModel(database.dao) as T
                }
            }
        }
    )*/

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        database = Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java,
            "notes.db"
        ).build()

        viewModel = NoteViewModel(database.dao)
        setContent {
            NotesAppWithComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    val state by viewModel.state.collectAsState()
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "NoteScreen") {
                    composable("NoteScreen") { NoteScreen( state = state,
                        navController = navController,
                        onEvent = viewModel::onEvent,
                        modifier = Modifier.fillMaxSize())
                    }
                    composable("AddNoteScreen") { AddNotesScreen(
                        state = state,
                        navController = navController,
                        onEvent = viewModel::onEvent,
                        modifier = Modifier.fillMaxSize()) }
                }
                   /* NavHost(navController = navController, startDestination = "NoteScreen") {
                        composable("NoteScreen") {
                            NoteScreen(
                                state = state,
                                navController = navController,
                                onEvent = viewModel::onEvent,
                                modifier = Modifier.fillMaxSize()
                            )
                            composable("AddNoteScreen") {
                                AddNotesScreen(
                                    state = state,
                                    navController = navController,
                                    onEvent = viewModel::onEvent,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                        }
                    }*/
                }
            }
        }


    }
}




