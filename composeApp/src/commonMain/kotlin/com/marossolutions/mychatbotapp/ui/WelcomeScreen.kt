package com.marossolutions.mychatbotapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marossolutions.mychatbotapp.viewmodel.WelcomeViewModel
import mychatbotapp.composeapp.generated.resources.Res
import mychatbotapp.composeapp.generated.resources.welcome_button_text
import mychatbotapp.composeapp.generated.resources.welcome_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun WelcomeScreen(viewModel: WelcomeViewModel = koinViewModel()) {

    WelcomeScreenContent(
        navigateToNextScreen = viewModel::navigateToNextScreen
    )
}

@Composable
fun WelcomeScreenContent(navigateToNextScreen: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(Res.string.welcome_title), fontSize = 32.sp)
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = navigateToNextScreen) {
            Text(text = stringResource(Res.string.welcome_button_text))
        }
    }
}