package com.marossolutions.mychatbotapp

import com.marossolutions.mychatbotapp.provider.NativeResponseProvider

suspend fun loadResourcesNative() {
    getNativeProvider().loadResources()
}

fun predictNative(message: String): String = getNativeProvider().predict(message)

private var nativeProvider: NativeResponseProvider? = null

private fun getNativeProvider() = nativeProvider
    ?: throw IllegalStateException("NativeResponseProvider not set")

fun setNativeResponseProvider(provider: NativeResponseProvider) {
    nativeProvider = provider
}
