package com.marossolutions.mychatbotapp.provider

interface NativeResponseProvider {
    suspend fun loadResources()
    fun predict(message: String): String
}
