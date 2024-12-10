package com.marossolutions.mychatbotapp.tensorflowlite

interface TensorFlowLiteInputProcessorService {
    suspend fun loadResources()
    fun predict(message: String): String
}