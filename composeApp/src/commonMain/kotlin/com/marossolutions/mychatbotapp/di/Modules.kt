package com.marossolutions.mychatbotapp.di

import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    includes(
        coreModule,
        serviceModule,
        repositoryModule,
        viewModelModule,
        navigationModule,
        networkModule
    )
}
