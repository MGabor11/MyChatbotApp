package com.marossolutions.mychatbotapp

actual suspend fun loadResourcesNative() {
    println("Loading native resources")
}

actual fun predictNative(message: String): String {
    return "predict"
}