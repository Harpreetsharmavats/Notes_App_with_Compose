package com.example.notesappwithcompose

import AddNoteScreen
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.notesappwithcompose.Data.NoteDatabase
import com.example.notesappwithcompose.Presentation.NoteScreen
import com.example.notesappwithcompose.Presentation.NoteViewModel
import com.example.notesappwithcompose.ui.theme.NotesAppWithComposeTheme


class MainActivity : ComponentActivity() {
    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java,
            "notes.db"
        ).build()
    }
    private val viewModel by viewModels<NoteViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NoteViewModel(database.dao) as T
                }
            }
        }
    )

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            NotesAppWithComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    val state by viewModel.state.collectAsState()
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "NoteScreen") {
                        composable("NoteScreen") {
                            NoteScreen(
                                state = state,
                                navController = navController,
                                onEvent = viewModel::onEvent,
                                modifier = Modifier.fillMaxSize() // ✅ Pass modifier
                            )
                            composable("AddNoteScreen") {
                                AddNoteScreen(
                                    state = state,
                                    navController = navController,
                                    onEvent = viewModel::onEvent,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }
            }
        }


    }
}




