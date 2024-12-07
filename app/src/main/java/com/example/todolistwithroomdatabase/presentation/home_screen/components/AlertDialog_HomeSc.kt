package com.example.todolistwithroomdatabase.presentation.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.todolistwithroomdatabase.domain.model.Todo
import com.example.todolistwithroomdatabase.presentation.MainViewModel
import com.example.todolistwithroomdatabase.presentation.common.taskTextStyle
import com.example.todolistwithroomdatabase.presentation.common.toastMsg
import kotlinx.coroutines.job

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialog_HomeSc(
    openDialog: Boolean,
    onClose:() -> Unit,
    mainViewModel: MainViewModel
){
    var text by remember { mutableStateOf("") }
    var isImportant by remember { mutableStateOf(false) }

    val todo = Todo(0, text, isImportant)

    val focusRequester = FocusRequester()
    val context = LocalContext.current

    if(openDialog){
        AlertDialog(
            title={
              Text(text ="TODO",
                  fontFamily = FontFamily.Serif,
                  color= Color(230,230,250)
              )
            },
            text={
                LaunchedEffect(
                    key1 = true,
                    block = {
                        coroutineContext.job.invokeOnCompletion {
                            focusRequester.requestFocus()
                        }
                    })
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black, shape = RoundedCornerShape(8.dp))
                ) {
                    TextField(
                        value = text,
                        onValueChange = {text=it},
                        placeholder = {
                            Text(
                                text= "ADD TASK",
                                fontFamily = FontFamily.Default,
                                color=Color.Black
                            )
                        },
                        shape= RectangleShape,
                        modifier= Modifier
                            .focusRequester(focusRequester),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if(text.isNotBlank()){
                                    mainViewModel.insertTodo(todo=todo)
                                    text=""
                                    isImportant=false
                                    onClose()
                                }
                                else{
                                    toastMsg(
                                        context,
                                        "NO TASK!"
                                    )
                                }
                            }
                        ),
                        trailingIcon = {
                            IconButton(onClick = { text="" }) {
                                Icon(
                                    imageVector = Icons.Rounded.Clear,
                                    contentDescription = null
                                )
                            }
                        },
                        textStyle = taskTextStyle,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color(230,230,250)
                        )
                    )
                    Row(
                        modifier=Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(text="Important",
                            fontFamily = FontFamily.Default,
                            fontSize=18.sp,
                            color=Color(230,230,250)
                        )
                        Spacer(modifier=Modifier.size(8.dp))
                        Checkbox(checked = isImportant,
                            onCheckedChange ={isImportant=it},
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color(128,0,128), // Color when the checkbox is checked
                                uncheckedColor = Color(230,230,250),     // Color when the checkbox is unchecked
                                checkmarkColor = Color(230,230,250)     // Color of the checkmark
                            ) )
                    }
                }
            },
            onDismissRequest = {
                onClose()
                text=""
                isImportant=false
            },
            confirmButton = {
                Button(onClick={
                    if(text.isNotBlank()){
                        mainViewModel.insertTodo(todo=todo)
                        text=""
                        isImportant=false
                        onClose()
                    }
                    else{
                        toastMsg(
                            context,
                            "NO TASK"
                        )
                    }
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(230,230,250), // Your desired background color
                        contentColor = Color(128,0,128) // Your desired content (text) color
                    )){
                    Text(text="SAVE")
                }
            },
            dismissButton={
                OutlinedButton(onClick = {
                    onClose()
                    text=""
                    isImportant=false
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(128,0,128), // Your desired background color
                        contentColor = Color(230,230,250) // Your desired content (text) color
                    )) {
                    Text(text="CLOSE")
                }
            },
            containerColor = Color.Black
            )

    }
}


