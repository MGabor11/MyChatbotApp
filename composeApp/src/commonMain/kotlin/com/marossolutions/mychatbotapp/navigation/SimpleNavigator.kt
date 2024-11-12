package com.marossolutions.mychatbotapp.navigation

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import com.marossolutions.mychatbotapp.navigation.screens.AppScreen

interface SimpleNavigator {

    val navigationEvents: SharedFlow<NavigationEvent>

    val currentAppScreen: StateFlow<AppScreen?>

    fun setCurrentAppScreen(screen: AppScreen?)

    fun navigateTo(screen: AppScreen, navigationOptions: NavigationOptions? = null)

    fun navigateUp()
}