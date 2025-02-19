package com.example.androidpracticumcustomview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.androidpracticumcustomview.ui.ComposeActivity
import com.example.androidpracticumcustomview.ui.XmlActivity

/*
Задание:
Реализуйте необходимые компоненты.
*/

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

    @Composable
    fun MainScreen() {
        Scaffold { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(onClick = {
                    startActivity(android.content.Intent(this@MainActivity, XmlActivity::class.java))
                }) {
                    Text("XmlActivity")
                }
                Button(onClick = {
                    startActivity(android.content.Intent(this@MainActivity, ComposeActivity::class.java))
                }) {
                    Text("ComposeActivity")
                }
            }
        }
    }
}