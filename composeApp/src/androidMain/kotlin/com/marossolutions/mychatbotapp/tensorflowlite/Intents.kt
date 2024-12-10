package com.marossolutions.mychatbotapp.tensorflowlite

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Intents(
    val intents: List<Intent>
)

@Serializable
data class Intent(
    val tag: String,
    val patterns: List<String>,
    val responses: List<String>,
    @SerialName("context_set") val contextSet: String,
    @SerialName("context_filter") val contextFilter: String
)
