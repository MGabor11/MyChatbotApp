package com.marossolutions.mychatbotapp.di

import com.marossolutions.mychatbotapp.service.ChatGPTService
import com.marossolutions.mychatbotapp.service.ChatGPTServiceImpl
import com.marossolutions.mychatbotapp.service.DispatcherProvider
import com.marossolutions.mychatbotapp.service.DispatcherProviderImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val serviceModule = module {
    singleOf(::DispatcherProviderImpl).bind<DispatcherProvider>()
    singleOf(::ChatGPTServiceImpl).bind<ChatGPTService>()
}