package com.marossolutions.mychatbotapp.di

import com.marossolutions.mychatbotapp.navigation.SimpleNavigator
import com.marossolutions.mychatbotapp.navigation.SimpleNavigatorImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val navigationModule = module {
    singleOf(::SimpleNavigatorImpl).bind<SimpleNavigator>()
}