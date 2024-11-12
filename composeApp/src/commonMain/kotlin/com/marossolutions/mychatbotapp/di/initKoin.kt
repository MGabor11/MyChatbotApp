package com.marossolutions.mychatbotapp.di

import com.marossolutions.mychatbotapp.di.platformModule
import com.marossolutions.mychatbotapp.di.sharedModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(sharedModule, platformModule)
    }
}
