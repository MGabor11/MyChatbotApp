package com.marossolutions.mychatbotapp.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.marossolutions.mychatbotapp.theme.AppTheme
import com.marossolutions.mychatbotapp.ui.WelcomeScreenContent

@Preview
@Composable
fun WelcomeScreenContentPreview() {
    AppTheme {
        WelcomeScreenContent { }
    }
}
