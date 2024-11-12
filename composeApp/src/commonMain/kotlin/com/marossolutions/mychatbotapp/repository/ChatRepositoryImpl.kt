package com.marossolutions.mychatbotapp.repository

import com.marossolutions.mychatbotapp.model.api.CompletionRequest
import com.marossolutions.mychatbotapp.model.api.Message
import com.marossolutions.mychatbotapp.model.domain.AnswerState
import com.marossolutions.mychatbotapp.model.domain.AIChatAnswer
import com.marossolutions.mychatbotapp.model.domain.UserChatMessage
import com.marossolutions.mychatbotapp.service.ChatGPTService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChatRepositoryImpl(
    private val chatGPTService: ChatGPTService
) : ChatRepository {

    private val _chatAnswerState = MutableStateFlow<AnswerState>(AnswerState.Init)

    override val chatAnswerState = _chatAnswerState.asStateFlow()

    override suspend fun sendMessage(chatMessage: UserChatMessage) {
        try {
            _chatAnswerState.value = AnswerState.Loading

            val response = chatGPTService.getAnswer(
                CompletionRequest(
                    model = chatMessage.model,
                    messages = listOf(
                        Message(
                            role = "user",
                            content = chatMessage.message
                        )
                    )
                )
            )
            _chatAnswerState.value = AnswerState.Success(
                AIChatAnswer(response.choices.first().message.content)
            )
        } catch (exception: Exception) {
            // TODO check it
            _chatAnswerState.value = AnswerState.Error
        }
    }
}
