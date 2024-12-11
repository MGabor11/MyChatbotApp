package com.marossolutions.mychatbotapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marossolutions.mychatbotapp.model.domain.ChatMessage
import com.marossolutions.mychatbotapp.model.domain.ModelsFetchingState
import com.marossolutions.mychatbotapp.model.domain.OpenAIModel
import com.marossolutions.mychatbotapp.navigation.SimpleNavigator
import com.marossolutions.mychatbotapp.navigation.screens.ScreenChatGPTChatbot
import com.marossolutions.mychatbotapp.navigation.screens.ScreenTensorflowLiteChatbot
import com.marossolutions.mychatbotapp.repository.ChatRepository
import com.marossolutions.mychatbotapp.viewmodel.ChatGPTChatbotViewModel.ChatGPTChatbotUiState
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private val chatRepository: ChatRepository,
    private val navigator: SimpleNavigator,
) : ViewModel() {

    val uiState = chatRepository.modelsFetchingState.map { state ->
        when (state) {
            ModelsFetchingState.Init,
            ModelsFetchingState.Loading -> HomeUiState.Loading

            ModelsFetchingState.Error -> HomeUiState.Error
            is ModelsFetchingState.Success -> HomeUiState.Content(openAIModels = state.models)
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(),
        initialValue = HomeUiState.Loading
    )

    init {
        viewModelScope.launch {
            chatRepository.fetchOpenAIModels()
        }
    }

    fun navigateToChatGPTChatbot() {
        navigator.navigateTo(ScreenChatGPTChatbot)
    }

    fun navigateToTensorflowChatbot() {
        navigator.navigateTo(ScreenTensorflowLiteChatbot)
    }

    internal sealed interface HomeUiState {
        data object Loading : HomeUiState

        data class Content(
            val openAIModels: List<OpenAIModel>,
        ) : HomeUiState

        data object Error : HomeUiState
    }
}
