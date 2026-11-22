package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notesapp.presentation.add_edit_note.AddEditNoteScreen
import com.example.notesapp.presentation.notes.NoteScreen
import com.example.notesapp.presentation.util.Screen
import com.example.notesapp.ui.theme.NotesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesAppTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    modifier = Modifier.padding(16.dp).padding(innerPadding)
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotesScreen.route
                        ){
                        composable(route = Screen.NotesScreen.route){
                            NoteScreen(navController)
                        }
                        composable(route = Screen.AddEditNoteScreen.route +
                        "?noteId={noteId}&noteColor={noteColo}",
                            arguments = listOf(
                                navArgument(
                                    name = "noteId"
                                ){
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "noteColor"
                                ){
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )){
                            backStackEntry -> val color = backStackEntry.arguments?.getInt("noteColor") ?: -1
                            AddEditNoteScreen(navController,color)
                        }
                    }
                }
            }
        }
    }
//}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotesAppTheme {
        Greeting("Android")
    }
}