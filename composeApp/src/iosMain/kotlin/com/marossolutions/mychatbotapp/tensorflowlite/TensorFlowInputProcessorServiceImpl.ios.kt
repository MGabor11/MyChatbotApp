package com.marossolutions.mychatbotapp.tensorflowlite

import com.marossolutions.mychatbotapp.loadResourcesNative
import com.marossolutions.mychatbotapp.predictNative

actual class TensorFlowLiteInputProcessorServiceImpl() : TensorFlowLiteInputProcessorService {

    override suspend fun loadResources() {
        loadResourcesNative()
    }

    override fun predict(message: String): String {
        return predictNative(message)
    }
}
