package com.marossolutions.mychatbotapp.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.marossolutions.mychatbotapp.navigation.screens.ScreenChatGPTChatbot
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import com.marossolutions.mychatbotapp.navigation.screens.ScreenHome
import com.marossolutions.mychatbotapp.navigation.screens.ScreenTensorflowLiteChatbot
import com.marossolutions.mychatbotapp.navigation.screens.ScreenWelcome
import com.marossolutions.mychatbotapp.ui.ChatGPTChatbotScreen
import com.marossolutions.mychatbotapp.ui.HomeScreen
import com.marossolutions.mychatbotapp.ui.TensorflowChatbotScreen
import com.marossolutions.mychatbotapp.ui.WelcomeScreen

@Composable
fun AppNavHost(
    simpleNavigator: SimpleNavigator,
    innerPadding: PaddingValues,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        modifier = Modifier.padding(innerPadding),
        startDestination = ScreenWelcome
    ) {
        composable<ScreenWelcome> {
            WelcomeScreen()
        }
        composable<ScreenHome> {
            HomeScreen()
        }
        composable<ScreenChatGPTChatbot> {
            ChatGPTChatbotScreen()
        }
        composable<ScreenTensorflowLiteChatbot> {
            TensorflowChatbotScreen()
        }
    }

    LaunchedEffect(Unit) {
        simpleNavigator.navigationEvents
            .onEach { navigateEvent ->
                when (navigateEvent) {
                    is NavigationEvent.ForwardNavigation -> navController.navigate(navigateEvent.screen) {
                        navigateEvent.navigationOptions?.let { navigationOptions ->
                            popUpTo(navigationOptions.popUpToScreen) {
                                inclusive = navigationOptions.popUpToInclusive
                            }
                        }
                    }

                    NavigationEvent.NavigateUp -> navController.navigateUp()
                }
            }
            .launchIn(this)

        navController.currentBackStackEntryFlow
            .onEach {
                simpleNavigator.setCurrentAppScreen(it.toAppScreen())
            }
            .launchIn(this)
    }
}
