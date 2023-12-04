package com.jj.readrover

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import com.jj.readrover.navigation.ReaderNavigation
import com.jj.readrover.ui.theme.ReadRoverTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // 해당 class가 HIlt를 사용하는 Android 진입점임을 나타냄
class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReadRoverTheme {
                ReaderApp() // ReaderApp Composable 함수를 호출하여 UI를 구성

            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun ReaderApp() {
    Surface(color = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxSize(), content = {

        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            ReaderNavigation() // ReaderNavigation Composable 함수를 호출하여 UI를 구성
        }

    })

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ReadRoverTheme {

    }
}