package com.example.todolistwithroomdatabase.presentation.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todolistwithroomdatabase.presentation.MainViewModel
import com.example.todolistwithroomdatabase.presentation.common.mySnackbar
import com.example.todolistwithroomdatabase.presentation.common.topAppBarTextStyle
import com.example.todolistwithroomdatabase.presentation.home_screen.components.AlertDialog_HomeSc
import com.example.todolistwithroomdatabase.presentation.home_screen.components.EmptyTaskScreen
import com.example.todolistwithroomdatabase.presentation.home_screen.components.TodoCard
import kotlin.math.round

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    onUpdate: (id: Int) -> Unit
) {
    val todos by mainViewModel.getAllTodos.collectAsStateWithLifecycle(initialValue = emptyList())

    var openDialog by rememberSaveable {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(title = {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color(230, 230, 250), fontSize = 34.sp, fontWeight = FontWeight.Bold)) {
                            append("To")
                        }
                        withStyle(style = SpanStyle(color = Color(128, 0, 128), fontSize = 34.sp, fontWeight = FontWeight.Bold)) {
                            append("Do.")
                        }
                    },
                    style = topAppBarTextStyle
                )
            },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black // Set your desired background color here
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { openDialog = true },
                containerColor = Color(128,0,128)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null,
                    tint = Color(230,230,250) // Set the color of the icon
                )
            }
        },
        containerColor = Color.Black
    ) { paddingValues ->
        AlertDialog_HomeSc(
            openDialog = openDialog,
            onClose = { openDialog = false },
            mainViewModel = mainViewModel
        )
        if (todos.isEmpty()) {
            EmptyTaskScreen(paddingValues = paddingValues)
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = todos,
                    key = { it.id }
                ) { todo ->
                    TodoCard(
                        todo = todo,
                        onDone = {
                            mainViewModel.deleteTodo(todo = todo)
                            mySnackbar(
                                scope = scope,
                                snackbarHostState = snackbarHostState,
                                msg = "Task \"${todo.task}\" completed",
                                actionLabel = "UNDO",
                                onAction = { mainViewModel.undoDeletedTodo() }
                            )
                        },
                        onUpdate = onUpdate
                    )
                }
            }
        }
    }
}
