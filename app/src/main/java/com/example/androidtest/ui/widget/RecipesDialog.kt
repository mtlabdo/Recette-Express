package com.example.androidtest.ui.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesDialog(message: String, buttonText: String, onButtonClick: () -> Unit = {}) {

    AlertDialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnClickOutside = false
        ),
    ) {
        Surface {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )

                Button(
                    onClick = onButtonClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(16.dp),

                    ) {
                    Text(color = Color.White, text = buttonText)
                }
            }
        }
    }
}
