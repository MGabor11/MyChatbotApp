package com.marossolutions.mychatbotapp.di

import com.marossolutions.mychatbotapp.tensorflowlite.TensorFlowLiteInputProcessorService
import com.marossolutions.mychatbotapp.tensorflowlite.TensorFlowLiteInputProcessorServiceImpl
import org.koin.dsl.module

actual val platformModule = module {
    single<TensorFlowLiteInputProcessorService> {
        TensorFlowLiteInputProcessorServiceImpl()
    }
}
