package com.marossolutions.mychatbotapp.di

import org.koin.dsl.bind
import org.koin.dsl.module
import com.marossolutions.mychatbotapp.repository.ChatRepository
import com.marossolutions.mychatbotapp.repository.ChatRepositoryImpl
import org.koin.core.module.dsl.singleOf

val repositoryModule = module {
    singleOf(::ChatRepositoryImpl).bind<ChatRepository>()
}