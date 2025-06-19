package com.marossolutions.mychatbotapp

suspend fun loadResourcesNative() {
    nativeProvider?.loadResources()
        ?: throw IllegalStateException("NativeResponseProvider not set")
}

fun predictNative(message: String): String {
    return nativeProvider?.predict(message)
        ?: throw IllegalStateException("NativeResponseProvider not set")
}

interface NativeResponseProvider {
    suspend fun loadResources()
    fun predict(message: String): String
}

private var nativeProvider: NativeResponseProvider? = null

fun setNativeResponseProvider(provider: NativeResponseProvider) {
    nativeProvider = provider
}
