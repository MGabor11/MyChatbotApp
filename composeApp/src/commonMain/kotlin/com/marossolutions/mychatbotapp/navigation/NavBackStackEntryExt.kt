package com.marossolutions.mychatbotapp.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.toRoute
import com.marossolutions.mychatbotapp.navigation.screens.AppScreen
import com.marossolutions.mychatbotapp.navigation.screens.ScreenChatGPTChatbot
import com.marossolutions.mychatbotapp.navigation.screens.ScreenHome
import com.marossolutions.mychatbotapp.navigation.screens.ScreenTensorflowLiteChatbot
import com.marossolutions.mychatbotapp.navigation.screens.ScreenWelcome

/**
 * This solution needs to be replaced, when toRoute method will be able to return the screen
 * from current route of NavBackStackEntry destination
 * https://stackoverflow.com/a/78495523
 */
fun NavBackStackEntry.toAppScreen(): AppScreen? = destination.route?.let { route ->
    when (route.substringBefore("?").substringBefore("/").substringAfterLast(".")) {
        ScreenWelcome::class.simpleName -> toRoute<ScreenWelcome>()
        ScreenHome::class.simpleName -> toRoute<ScreenHome>()
        ScreenChatGPTChatbot::class.simpleName -> toRoute<ScreenChatGPTChatbot>()
        ScreenTensorflowLiteChatbot::class.simpleName -> toRoute<ScreenTensorflowLiteChatbot>()
        else -> error("Route: $route, is not recognized")
    }
}
