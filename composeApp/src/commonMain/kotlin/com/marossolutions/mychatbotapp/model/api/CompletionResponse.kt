package com.marossolutions.mychatbotapp.model.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompletionResponse(
    val id: String,
    val created: Long,
    val model: String,
    @SerialName("system_fingerprint") val systemFingerprint: String?,
    val choices: List<Choice>,
    val usage: Usage
)
