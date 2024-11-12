package com.marossolutions.mychatbotapp.model.domain

data class UserChatMessage(
    val message: String,
    val model: String = "gpt-3.5-turbo"
) : ChatMessage
