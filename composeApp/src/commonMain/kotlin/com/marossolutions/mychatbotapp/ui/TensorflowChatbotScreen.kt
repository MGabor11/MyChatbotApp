package com.marossolutions.mychatbotapp.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.marossolutions.mychatbotapp.components.FullScreenLoading
import com.marossolutions.mychatbotapp.model.domain.AIChatAnswer
import com.marossolutions.mychatbotapp.model.domain.ChatMessage
import com.marossolutions.mychatbotapp.model.domain.UserChatMessage
import com.marossolutions.mychatbotapp.ui.dialog.ErrorDialog
import com.marossolutions.mychatbotapp.viewmodel.ChatGPTChatbotViewModel
import com.marossolutions.mychatbotapp.viewmodel.ChatGPTChatbotViewModel.ChatGPTChatbotUiState
import com.marossolutions.mychatbotapp.viewmodel.TensorflowChatbotViewModel
import com.marossolutions.mychatbotapp.viewmodel.TensorflowChatbotViewModel.TensorflowChatbotUiState
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun TensorflowChatbotScreen(viewModel: TensorflowChatbotViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TensorflowChatbotScreenContent(
        uiState = uiState,
        sendMessage = viewModel::sendMessage
    )
}

@Composable
private fun TensorflowChatbotScreenContent(uiState: TensorflowChatbotUiState, sendMessage: (String) -> Unit) {
    when (val state = uiState) {
        TensorflowChatbotUiState.Error -> Box {
            FullScreenLoading()
            var isDialogOpen by remember { mutableStateOf(true) }
            if (isDialogOpen) {
                ErrorDialog {
                    isDialogOpen = false
                }
            }
        }

        TensorflowChatbotUiState.Loading -> FullScreenLoading()
        is TensorflowChatbotUiState.Content -> Column(
            modifier = Modifier.fillMaxSize()
                .padding(16.dp),
        ) {
            ChatHistory(chatHistory = state.chatMessages, modifier = Modifier.weight(1f))
            ChatInput(sendMessage = sendMessage)
        }
    }
}

@Composable
private fun ChatHistory(chatHistory: List<ChatMessage>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(chatHistory) {
            ChatBubble(it)
        }
    }
}

@Composable
private fun ChatBubble(message: ChatMessage) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = if (message is UserChatMessage) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = if (message is UserChatMessage) Color(0xFFDCF8C6) else MaterialTheme.colors.primary,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = when (message) {
                    is UserChatMessage -> message.message
                    is AIChatAnswer -> message.answer
                },
                modifier = Modifier.padding(12.dp),
                color = if (message is UserChatMessage) Color.Black else Color.White
            )
        }
    }
}

@Composable
private fun ChatInput(sendMessage: (String) -> Unit) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }

    Row(
        modifier = Modifier.fillMaxWidth(),
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