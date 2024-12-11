package com.marossolutions.mychatbotapp.service

import com.marossolutions.mychatbotapp.model.api.CompletionRequest
import com.marossolutions.mychatbotapp.model.api.CompletionResponse
import com.marossolutions.mychatbotapp.model.api.ModelsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.withContext

class ChatGPTServiceImpl(
    private val httpClient: HttpClient,
    private val dispatcherProvider: DispatcherProvider,
) : ChatGPTService {

    override suspend fun getAnswer(request: CompletionRequest): CompletionResponse =
        withContext(dispatcherProvider.io) {
            val response = httpClient.post("chat/completions") {
                setBody(request)
            }.body<CompletionResponse>()
            return@withContext response
        }

    override suspend fun getAvailableModels(): ModelsResponse = withContext(dispatcherProvider.io) {
        val response = httpClient.get("models").body<ModelsResponse>()
        return@withContext response
    }
}