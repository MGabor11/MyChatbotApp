package com.marossolutions.mychatbotapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.marossolutions.mychatbotapp.components.ChatHistory
import com.marossolutions.mychatbotapp.components.ChatInput
import com.marossolutions.mychatbotapp.components.FullScreenLoading
import com.marossolutions.mychatbotapp.ui.dialog.ErrorDialog
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
