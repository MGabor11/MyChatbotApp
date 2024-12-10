package com.marossolutions.mychatbotapp.viewmodel

import androidx.lifecycle.ViewModel
import com.marossolutions.mychatbotapp.navigation.SimpleNavigator
import com.marossolutions.mychatbotapp.navigation.screens.ScreenChatGPTChatbot
import com.marossolutions.mychatbotapp.navigation.screens.ScreenTensorflowLiteChatbot

internal class HomeViewModel(
    private val navigator: SimpleNavigator,
) : ViewModel() {

    fun navigateToChatGPTChatbot() {
        navigator.navigateTo(ScreenChatGPTChatbot)
    }

    fun navigateToTensorflowChatbot() {
        navigator.navigateTo(ScreenTensorflowLiteChatbot)
    }
}