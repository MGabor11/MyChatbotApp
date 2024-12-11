package com.marossolutions.mychatbotapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.marossolutions.mychatbotapp.components.FullScreenLoading
import com.marossolutions.mychatbotapp.model.domain.OpenAIModel
import com.marossolutions.mychatbotapp.ui.dialog.ErrorDialog
import com.marossolutions.mychatbotapp.viewmodel.HomeViewModel
import com.marossolutions.mychatbotapp.viewmodel.HomeViewModel.HomeUiState
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreenContent(
        uiState = uiState,
        navigateToChatGPTChatbot = viewModel::navigateToChatGPTChatbot,
        navigateToTensorflowChatbot = viewModel::navigateToTensorflowChatbot
    )
}

@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    navigateToChatGPTChatbot: () -> Unit,
    navigateToTensorflowChatbot: () -> Unit
) {
    when (val state = uiState) {
        HomeUiState.Error -> Box {
            FullScreenLoading()
            var isDialogOpen by remember { mutableStateOf(true) }
            if (isDialogOpen) {
                ErrorDialog {
                    isDialogOpen = false
                }
            }
        }

        HomeUiState.Loading -> FullScreenLoading()
        is HomeUiState.Content -> Column(
            modifier = Modifier.fillMaxSize()
                .padding(16.dp),
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

                Spacer(modifier = Modifier.height(16.dp))

                OpenAIModelList(state.openAIModels)
            }
        }
    }
}

@Composable
fun OpenAIModelList(models: List<OpenAIModel>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(models) { model ->
            OpenAIModelItem(model)
        }
    }
}

@Composable
fun OpenAIModelItem(model: OpenAIModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.LightGray)
            .padding(8.dp),
    ) {
        Text(
            text = "ID / Name: ${model.id}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Created: ${model.created.toLocalDateTime(TimeZone.currentSystemDefault())}",
            style = MaterialTheme.typography.bodySmall,
        )
        Text(
            text = "Owned By: ${model.ownedBy}",
            style = MaterialTheme.typography.bodySmall,
        )
    }
}
