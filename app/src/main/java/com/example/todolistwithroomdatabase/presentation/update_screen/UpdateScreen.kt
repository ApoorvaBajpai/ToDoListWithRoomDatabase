package com.example.todolistwithroomdatabase.presentation.update_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolistwithroomdatabase.presentation.MainViewModel
import com.example.todolistwithroomdatabase.presentation.common.taskTextStyle
import com.example.todolistwithroomdatabase.presentation.common.topAppBarTextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreen(
    id: Int,
    mainViewModel: MainViewModel,
    onBack: () -> Unit
){
    val task = mainViewModel.todo.task
    val isImportant = mainViewModel.todo.isImportant
    
    LaunchedEffect(
        key1 = true,
        block = {
            mainViewModel.getTodoById(id = id)
        })

    Scaffold(
        topBar= {
            TopAppBar(title = {
                Text(
                    text = "UPDATE TASK",
                    fontWeight = FontWeight.SemiBold,
                    style = topAppBarTextStyle
                )
            },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription=null,
                            tint= Color(128,0,128),
                            modifier=Modifier
                                .size(30.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black // Set your desired background color here
                ),)
        },
        containerColor = Color.Black
    ){  paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.size(16.dp))
            Icon(
                imageVector = Icons.Rounded.Edit,
                contentDescription = null,
                tint= Color(230,230,250),
                modifier=Modifier
                    .size(100.dp)
            )
            Spacer(modifier=Modifier.size(16.dp))
            TextField(
                value=task,
                onValueChange={ newValue ->
                    mainViewModel.updateTask(newValue=newValue)
                },
                modifier=Modifier
                    .fillMaxWidth(.9f),
                label={
                    Text(
                        text="TASK",
                        fontFamily = FontFamily.Default,
                        color = Color.Black
                    )
                },
                shape= RectangleShape,
                keyboardOptions = KeyboardOptions(
                    KeyboardCapitalization.Sentences
                ),
                textStyle = taskTextStyle,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(230,230,250)
                )
            )
            Spacer(modifier = Modifier.size(8.dp))
            Row(
                modifier=Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text= "Important",
                    fontFamily = FontFamily.Default,
                    fontSize = 18.sp,
                    color = Color(230,230,250)
                )
                Spacer(modifier=Modifier.size(8.dp))
                Checkbox(
                    checked=isImportant,
                    onCheckedChange = {newValue ->
                        mainViewModel.updateIsImportant(newValue=newValue)
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(128,0,128), // Color when the checkbox is checked
                        uncheckedColor = Color(230,230,250),     // Color when the checkbox is unchecked
                        checkmarkColor = Color(230,230,250)     // Color of the checkmark
                    )
                )
            }
            Spacer(modifier=Modifier.size(8.dp))
            Button(onClick={
                mainViewModel.updateTodo(mainViewModel.todo)
                onBack()
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(128,0,128), // Your desired background color
                    contentColor = Color(230,230,250) // Your desired content (text) color
                )){
                Text(
                    text= "SAVE",
                    fontSize = 16.sp,
                )
            }
        }

    }
}