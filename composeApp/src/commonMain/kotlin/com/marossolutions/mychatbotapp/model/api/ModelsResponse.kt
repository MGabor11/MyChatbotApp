package com.marossolutions.mychatbotapp.model.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ModelsResponse(
    @SerialName("object") val objectType: String,
    @SerialName("data") val models: List<Model>,
)

@Serializable
data class Model(
    val id: String,
    @SerialName("object") val objectType: String,
    val created: Long,
     @SerialName("owned_by")  val ownedBy: String,
)
