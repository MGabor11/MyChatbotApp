package com.marossolutions.mychatbotapp.repository

import com.marossolutions.mychatbotapp.model.api.CompletionRequest
import com.marossolutions.mychatbotapp.model.api.Message
import com.marossolutions.mychatbotapp.model.domain.AnswerState
import com.marossolutions.mychatbotapp.model.domain.AIChatAnswer
import com.marossolutions.mychatbotapp.model.domain.ModelsFetchingState
import com.marossolutions.mychatbotapp.model.domain.OpenAIModel
import com.marossolutions.mychatbotapp.model.domain.UserChatMessage
import com.marossolutions.mychatbotapp.service.ChatGPTService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.Instant

private const val COMPLETION_REQUEST_ROLE = "user"

class ChatRepositoryImpl(
    private val chatGPTService: ChatGPTService
) : ChatRepository {

    private val _chatAnswerState = MutableStateFlow<AnswerState>(AnswerState.Init)

    private val _modelsFetchingState =
        MutableStateFlow<ModelsFetchingState>(ModelsFetchingState.Init)

    override val chatAnswerState = _chatAnswerState.asStateFlow()

    override val modelsFetchingState = _modelsFetchingState.asStateFlow()

    override suspend fun sendMessage(message: UserChatMessage) {
        try {
            _chatAnswerState.value = AnswerState.Loading

            val response = chatGPTService.getAnswer(
                CompletionRequest(
                    model = message.model,
                    messages = listOf(
                        Message(
                            role = COMPLETION_REQUEST_ROLE,
                            content = message.message
                        )
                    )
                )
            )
            _chatAnswerState.value = AnswerState.Success(
                AIChatAnswer(response.choices.first().message.content)
            )
        } catch (exception: Exception) {
            _chatAnswerState.value = AnswerState.Error
        }
    }

    override suspend fun fetchOpenAIModels() {
        try {
            _modelsFetchingState.value = ModelsFetchingState.Loading

            val response = chatGPTService.getAvailableModels()

            _modelsFetchingState.value = ModelsFetchingState.Success(
                response.models.map { model ->
                    OpenAIModel(
                        id = model.id,
                        created = Instant.fromEpochSeconds(model.created),
                        ownedBy = model.ownedBy
                    )
                }.sortedBy { it.id }
            )
        } catch (exception: Exception) {
            _modelsFetchingState.value = ModelsFetchingState.Error
        }
    }
}
