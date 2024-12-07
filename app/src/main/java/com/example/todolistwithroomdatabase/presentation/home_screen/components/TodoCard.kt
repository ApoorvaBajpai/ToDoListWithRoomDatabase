package com.example.todolistwithroomdatabase.presentation.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.todolistwithroomdatabase.domain.model.Todo
import com.example.todolistwithroomdatabase.presentation.common.taskTextStyle

@Composable
fun TodoCard(
    todo: Todo,
    onDone: ()->Unit,
    onUpdate: (id: Int) -> Unit
){
    Card(
        modifier=Modifier
            .fillMaxWidth()
    ){

        Row(
            modifier=Modifier
                .fillMaxWidth()
                .background(color=Color(230,230,250)),
            verticalAlignment=Alignment.CenterVertically
        ){
            IconButton(
                onClick={ onDone() },
                modifier=Modifier
                    .weight(1f)
            ){
                Icon(
                    imageVector= Icons.Rounded.CheckCircle,
                    contentDescription=null,
                    tint = Color(128,0,128)
                )
            }
            Text(text=todo.task,
                maxLines=2,
                overflow= TextOverflow.Ellipsis,
                modifier=Modifier
                    .weight(8f),
                style= taskTextStyle
            )
            if(todo.isImportant){
                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = null,
                    tint = Color(128,0,128),
                    modifier=Modifier
                        .weight(1f)
                )
            }
            IconButton(
                onClick = { onUpdate(todo.id) },
                modifier=Modifier
                    .weight(1f)
            ){
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = null,
                    tint = Color(128,0,128)
                )
            }
        }

    }

}