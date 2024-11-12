package com.marossolutions.mychatbotapp.di

import com.marossolutions.mychatbotapp.BuildKonfig
import com.marossolutions.mychatbotapp.di.qualifier.openAIApiKey
import org.koin.dsl.module

val coreModule = module {
    single<String>(qualifier = openAIApiKey) { BuildKonfig.OPEN_AI_API_KEY }.toString()
}