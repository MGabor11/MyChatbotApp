package com.marossolutions.mychatbotapp.di

import com.marossolutions.mychatbotapp.tensorflowlite.TensorFlowLiteInputProcessorService
import com.marossolutions.mychatbotapp.tensorflowlite.TensorFlowLiteInputProcessorServiceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule = module {
    singleOf(::TensorFlowLiteInputProcessorServiceImpl).bind<TensorFlowLiteInputProcessorService>()
    /*single<TensorFlowLiteInputProcessorService> {
        TensorFlowLiteInputProcessorServiceImpl(get())
    }*/
}
