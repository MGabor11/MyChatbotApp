package com.marossolutions.mychatbotapp.service

import com.marossolutions.mychatbotapp.model.api.CompletionRequest
import com.marossolutions.mychatbotapp.model.api.CompletionResponse

interface ChatGPTService {

    suspend fun getAnswer(request: CompletionRequest): CompletionResponse
}
