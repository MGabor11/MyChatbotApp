package com.marossolutions.mychatbotapp.di

import com.marossolutions.mychatbotapp.viewmodel.WelcomeViewModel
import com.marossolutions.mychatbotapp.viewmodel.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::WelcomeViewModel)
    viewModelOf(::HomeViewModel)
}