package com.marossolutions.mychatbotapp.navigation

import com.marossolutions.mychatbotapp.navigation.screens.AppScreen

sealed interface NavigationEvent {

    data object NavigateUp : NavigationEvent

    data class ForwardNavigation(
        val screen: AppScreen,
        val navigationOptions: NavigationOptions? = null
    ) : NavigationEvent
}
