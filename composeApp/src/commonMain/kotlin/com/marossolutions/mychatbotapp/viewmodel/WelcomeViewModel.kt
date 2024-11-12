package com.marossolutions.mychatbotapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marossolutions.mychatbotapp.navigation.screens.ScreenHome
import com.marossolutions.mychatbotapp.navigation.screens.ScreenWelcome
import com.marossolutions.mychatbotapp.navigation.NavigationOptions
import com.marossolutions.mychatbotapp.navigation.SimpleNavigator
import kotlinx.coroutines.launch

internal class WelcomeViewModel(
    private val navigator: SimpleNavigator,
) : ViewModel() {

    fun navigateToNextScreen() {
        viewModelScope.launch {
            navigator.navigateTo(
                screen = ScreenHome,
                navigationOptions = NavigationOptions(
                    popUpToScreen = ScreenWelcome,
                    popUpToInclusive = true
                )
            )
        }
    }
}
