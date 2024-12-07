package com.example.todolistwithroomdatabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.todolistwithroomdatabase.presentation.MainViewModel
import com.example.todolistwithroomdatabase.presentation.navigation.AppNavigation
import com.example.todolistwithroomdatabase.ui.theme.ToDoListWithRoomDatabaseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListWithRoomDatabaseTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black) // Set background to black
                ) {
                    AppNavigation(mainViewModel = mainViewModel)
                }
            }
        }
    }
}


