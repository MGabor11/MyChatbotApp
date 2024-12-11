package com.marossolutions.mychatbotapp.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
internal fun ChatInput(sendMessage: (String) -> Unit, modifier: Modifier = Modifier) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
                .border(1.dp, Color.Gray, MaterialTheme.shapes.small)
                .padding(8.dp)
        )

        Button(
            onClick = {
                val message = textFieldValue.text.trim()
                if (message.isNotEmpty()) {
                    sendMessage(message)
                    textFieldValue = TextFieldValue("") // Clear input
                }
            },
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text("Send")
        }
    }
}
