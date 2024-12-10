package com.marossolutions.mychatbotapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marossolutions.mychatbotapp.model.domain.AIChatAnswer
import com.marossolutions.mychatbotapp.model.domain.AnswerState
import com.marossolutions.mychatbotapp.model.domain.ChatMessage
import com.marossolutions.mychatbotapp.model.domain.UserChatMessage
import com.marossolutions.mychatbotapp.tensorflowlite.TensorFlowLiteInputProcessorService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class TensorflowChatbotViewModel(
    private val tensorFlowLiteInputProcessorService: TensorFlowLiteInputProcessorService,
) : ViewModel() {

    private val chatHistory = MutableStateFlow<List<ChatMessage>>(emptyList())
    private val answerState = MutableSharedFlow<AnswerState>()

    init {
        viewModelScope.launch {
            answerState.emit(AnswerState.Loading)
            tensorFlowLiteInputProcessorService.loadResources()

            answerState.emit(AnswerState.Init)
        }
    }

    val uiState = combine(answerState, chatHistory) { answerState, chatHistory ->
        when (answerState) {
            AnswerState.Error -> TensorflowChatbotUiState.Error
            AnswerState.Loading -> TensorflowChatbotUiState.Loading
            AnswerState.Init -> TensorflowChatbotUiState.Content(chatHistory)
            is AnswerState.Success -> TensorflowChatbotUiState.Content(chatHistory + answerState.answer)
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(),
        initialValue = TensorflowChatbotUiState.Loading
    )

    fun sendMessage(message: String) {
        (uiState.value as? TensorflowChatbotUiState.Content)?.chatMessages
            ?.lastOrNull()
            .takeIf { it is AIChatAnswer }
            ?.let { lastAnswer ->
                insertMessageToChatHistory(lastAnswer)
            }

        viewModelScope.launch {
            answerState.emit(AnswerState.Loading)
            val userChatMessage = UserChatMessage(message)
            val response = tensorFlowLiteInputProcessorService.predict(userChatMessage.message)
            insertMessageToChatHistory(userChatMessage)

            answerState.emit(AnswerState.Success(AIChatAnswer(response)))
        }
    }

    private fun insertMessageToChatHistory(message: ChatMessage) {
        chatHistory.value += message
    }

    internal sealed interface TensorflowChatbotUiState {
        data object Loading : TensorflowChatbotUiState

        data class Content(
            val chatMessages: List<ChatMessage>,
        ) : TensorflowChatbotUiState

        data object Error : TensorflowChatbotUiState
    }
}
