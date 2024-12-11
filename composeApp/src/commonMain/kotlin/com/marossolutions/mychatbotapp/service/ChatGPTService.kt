package com.marossolutions.mychatbotapp.service

import com.marossolutions.mychatbotapp.model.api.CompletionRequest
import com.marossolutions.mychatbotapp.model.api.CompletionResponse
import com.marossolutions.mychatbotapp.model.api.ModelsResponse

interface ChatGPTService {

    suspend fun getAnswer(request: CompletionRequest): CompletionResponse

    suspend fun getAvailableModels(): ModelsResponse
}
