package com.marossolutions.mychatbotapp.di

import com.marossolutions.mychatbotapp.tensorflowlite.TensorFlowLiteInputProcessorService
import com.marossolutions.mychatbotapp.tensorflowlite.TensorFlowLiteInputProcessorServiceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformModule = module {
    single<TensorFlowLiteInputProcessorService> {
        val context = androidContext()
        TensorFlowLiteInputProcessorServiceImpl(
            context = context,
            assetManager = context.assets,
            dispatcherProvider = get()
        )
    }
}
