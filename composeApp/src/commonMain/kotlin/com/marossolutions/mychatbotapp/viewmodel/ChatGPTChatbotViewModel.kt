package com.marossolutions.mychatbotapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marossolutions.mychatbotapp.model.domain.AIChatAnswer
import com.marossolutions.mychatbotapp.model.domain.AnswerState
import com.marossolutions.mychatbotapp.model.domain.ChatMessage
import com.marossolutions.mychatbotapp.model.domain.UserChatMessage
import com.marossolutions.mychatbotapp.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class ChatGPTChatbotViewModel(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val chatHistory = MutableStateFlow<List<ChatMessage>>(emptyList())

    val uiState = combine(chatRepository.chatAnswerState, chatHistory) { answerState, chatHistory ->
        when (answerState) {
            AnswerState.Error -> ChatGPTChatbotUiState.Error
            AnswerState.Loading -> ChatGPTChatbotUiState.Loading
            AnswerState.Init -> ChatGPTChatbotUiState.Content(chatHistory)
            is AnswerState.Success -> ChatGPTChatbotUiState.Content(chatHistory + answerState.answer)
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(),
        initialValue = ChatGPTChatbotUiState.Loading
    )

    fun sendMessage(message: String) {
        (uiState.value as? ChatGPTChatbotUiState.Content)?.chatMessages
            ?.lastOrNull()
            .takeIf { it is AIChatAnswer }
            ?.let { lastAnswer ->
                insertMessageToChatHistory(lastAnswer)
            }

        viewModelScope.launch {
            val userChatMessage = UserChatMessage(message)
            chatRepository.sendMessage(userChatMessage)
            if (chatRepository.chatAnswerState.first() is AnswerState.Success) {
                insertMessageToChatHistory(userChatMessage)
            }
        }
    }

    private fun insertMessageToChatHistory(message: ChatMessage) {
        chatHistory.value += message
    }

    internal sealed interface ChatGPTChatbotUiState {
        data object Loading : ChatGPTChatbotUiState

        data class Content(
            val chatMessages: List<ChatMessage>,
        ) : ChatGPTChatbotUiState

        data object Error : ChatGPTChatbotUiState
    }
}