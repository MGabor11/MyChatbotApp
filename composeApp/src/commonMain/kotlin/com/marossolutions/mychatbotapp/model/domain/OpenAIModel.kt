package com.marossolutions.mychatbotapp.model.domain

import kotlinx.datetime.Instant

data class OpenAIModel(
    val id: String,
    val created: Instant,
    val ownedBy: String,
)
