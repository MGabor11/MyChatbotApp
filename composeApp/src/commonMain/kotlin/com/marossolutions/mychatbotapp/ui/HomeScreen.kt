package com.marossolutions.mychatbotapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.marossolutions.mychatbotapp.viewmodel.HomeViewModel
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
