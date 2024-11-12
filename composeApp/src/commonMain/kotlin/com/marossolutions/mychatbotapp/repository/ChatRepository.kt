package com.marossolutions.mychatbotapp.repository

import com.marossolutions.mychatbotapp.model.domain.AnswerState
import com.marossolutions.mychatbotapp.model.domain.UserChatMessage
import kotlinx.coroutines.flow.StateFlow

interface ChatRepository {

    val chatAnswerState : StateFlow<AnswerState>

    suspend fun sendMessage(message: UserChatMessage)
}
