package com.marossolutions.mychatbotapp.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.marossolutions.mychatbotapp.model.domain.AIChatAnswer
import com.marossolutions.mychatbotapp.model.domain.ChatMessage
import com.marossolutions.mychatbotapp.model.domain.UserChatMessage

@Composable
internal fun ChatBubble(message: ChatMessage) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = when (message) {
            is UserChatMessage -> Alignment.CenterEnd
            is AIChatAnswer -> Alignment.CenterStart
        }
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = when (message) {
                is UserChatMessage -> MaterialTheme.colors.secondary
                is AIChatAnswer -> MaterialTheme.colors.primary
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = when (message) {
                    is UserChatMessage -> message.message
                    is AIChatAnswer -> message.answer
                },
                modifier = Modifier.padding(12.dp),
                color = when (message) {
                    is UserChatMessage -> Color.Black
                    is AIChatAnswer -> Color.White
                }
            )
        }
    }
}
