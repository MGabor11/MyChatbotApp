package com.marossolutions.mychatbotapp

import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.marossolutions.mychatbotapp.navigation.AppNavHost
import com.marossolutions.mychatbotapp.navigation.screens.ScreenHome
import com.marossolutions.mychatbotapp.navigation.screens.ScreenWelcome
import com.marossolutions.mychatbotapp.navigation.SimpleNavigator
import com.marossolutions.mychatbotapp.navigation.screens.ScreenChatGPTChatbot
import com.marossolutions.mychatbotapp.navigation.screens.ScreenTensorflowLiteChatbot
import com.marossolutions.mychatbotapp.theme.AppTheme
import mychatbotapp.composeapp.generated.resources.Res
import mychatbotapp.composeapp.generated.resources.chat_gpt_chatbot_title
import mychatbotapp.composeapp.generated.resources.home_title
import mychatbotapp.composeapp.generated.resources.tensorflow_lite_chatbot_title
import mychatbotapp.composeapp.generated.resources.welcome_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    AppTheme {
        KoinContext {
            val simpleNavigator = koinInject<SimpleNavigator>()
            val navController: NavHostController = rememberNavController()
            Scaffold(
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        title = {
                            val appScreen by simpleNavigator.currentAppScreen.collectAsStateWithLifecycle()
                            val titleRes by remember {
                                derivedStateOf {
                                    when (appScreen) {
                                        is ScreenWelcome -> Res.string.welcome_title
                                        is ScreenHome -> Res.string.home_title
                                        is ScreenChatGPTChatbot -> Res.string.chat_gpt_chatbot_title
                                        is ScreenTensorflowLiteChatbot -> Res.string.tensorflow_lite_chatbot_title
                                        else -> null
                                    }
                                }
                            }
                            titleRes?.let {
                                Text(stringResource(it))
                            }
                        },
                        navigationIcon = {
                            val backStack by navController.currentBackStack.collectAsStateWithLifecycle()
                            val showBackButton by remember {
                                derivedStateOf {
                                    backStack.filterNot { it.destination.route == null }.size > 1
                                }
                            }

                            if (showBackButton) {
                                IconButton(onClick = { simpleNavigator.navigateUp() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    )
                }
            ) { innerPadding ->
                AppNavHost(
                    simpleNavigator = simpleNavigator,
                    innerPadding = innerPadding,
                    navController = navController,
                )
            }
        }
    }
}