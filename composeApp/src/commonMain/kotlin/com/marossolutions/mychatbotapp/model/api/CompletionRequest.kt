package com.marossolutions.mychatbotapp.model.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CompletionRequest(
    @SerialName("model") val model: String,
    @SerialName("messages") val messages: List<Message>
)
