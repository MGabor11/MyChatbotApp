package com.marossolutions.mychatbotapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marossolutions.mychatbotapp.model.domain.AnswerState
import com.marossolutions.mychatbotapp.model.domain.AIChatAnswer
import com.marossolutions.mychatbotapp.model.domain.ChatMessage
import com.marossolutions.mychatbotapp.model.domain.UserChatMessage
import com.marossolutions.mychatbotapp.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val chatHistory = MutableStateFlow<List<ChatMessage>>(emptyList())

    val uiState = combine(chatRepository.chatAnswerState, chatHistory) { answerState, chatHistory ->
        when (answerState) {
            AnswerState.Error -> HomeUiState.Error
            AnswerState.Loading -> HomeUiState.Loading
            AnswerState.Init -> HomeUiState.Content(chatHistory)
            is AnswerState.Success -> HomeUiState.Content(chatHistory + answerState.answer)
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(),
        initialValue = HomeUiState.Loading
    )

    fun sendMessage(message: String) {
        (uiState.value as? HomeUiState.Content)?.chatMessages
            ?.lastOrNull()
            .takeIf { it is AIChatAnswer }
            ?.let { lastAnswer ->
                updateChatHistory(lastAnswer)
            }

        viewModelScope.launch {
            val userChatMessage = UserChatMessage(message)
            chatRepository.sendMessage(userChatMessage)
            if (chatRepository.chatAnswerState.first() is AnswerState.Success) {
                updateChatHistory(userChatMessage)
            }
        }
    }

    private fun updateChatHistory(message: ChatMessage) {
        chatHistory.value += message
    }

    internal sealed interface HomeUiState {
        data object Loading : HomeUiState

        data class Content(
            val chatMessages: List<ChatMessage>,
        ) : HomeUiState

        data object Error : HomeUiState
    }
}