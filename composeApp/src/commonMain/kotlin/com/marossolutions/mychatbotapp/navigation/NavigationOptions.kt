package com.marossolutions.mychatbotapp.navigation

import com.marossolutions.mychatbotapp.navigation.screens.AppScreen

data class NavigationOptions(
    val popUpToScreen: AppScreen,
    val popUpToInclusive: Boolean
)
