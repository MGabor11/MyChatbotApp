package com.marossolutions.mychatbotapp

expect suspend fun loadResourcesNative()
expect fun predictNative(message: String): String