package com.marossolutions.mychatbotapp.tensorflowlite

import com.marossolutions.mychatbotapp.loadResourcesNative
import com.marossolutions.mychatbotapp.predictNative
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield

actual class TensorFlowLiteInputProcessorServiceImpl() : TensorFlowLiteInputProcessorService {

    override suspend fun loadResources() {
        withContext(Dispatchers.Default) {
            loadResourcesNative()
            //yield()
        }
    }

    override fun predict(message: String): String {
        return predictNative(message)
    }
}
