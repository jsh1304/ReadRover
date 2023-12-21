package com.jj.readrover.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun Home(navController: NavHostController) {
    Scaffold(topBar = {},
            floatingActionButton = {
                FABContent{
                    
                }
            }) {
            
            Surface(modifier = Modifier.fillMaxSize()) {
                
            }
    }
}


@Composable
fun FABContent(onTap: (String) -> Unit) {
    FloatingActionButton(onClick = { onTap("") },
        shape = RoundedCornerShape(50.dp),
        backgroundColor = MaterialTheme.colors.background) {
        Icon(imageVector = Icons.Default.Add,
            contentDescription = "책 추가",
            tint = MaterialTheme.colors.onSecondary)
    }

}
