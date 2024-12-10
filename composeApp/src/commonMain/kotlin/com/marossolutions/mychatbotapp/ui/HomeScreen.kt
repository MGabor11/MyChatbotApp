package com.marossolutions.mychatbotapp.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.marossolutions.mychatbotapp.viewmodel.HomeViewModel
import com.marossolutions.mychatbotapp.viewmodel.HomeViewModel.*
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {

    HomeScreenContent(
        navigateToChatGPTChatbot = viewModel::navigateToChatGPTChatbot,
        navigateToTensorflowChatbot = viewModel::navigateToTensorflowChatbot
    )
}

@Composable
private fun HomeScreenContent(
    navigateToChatGPTChatbot: () -> Unit,
    navigateToTensorflowChatbot: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
    ) {
        Button(onClick = navigateToChatGPTChatbot) {
            Text("ChatGPT Chatbot")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = navigateToTensorflowChatbot) {
            Text("Tensorflow Chatbot")
        }
    }
}
