package com.marossolutions.mychatbotapp.model.domain

sealed interface AnswerState {
    data object Init : AnswerState
    data object Loading : AnswerState
    data object Error : AnswerState
    data class Success(val answer: AIChatAnswer) : AnswerState
}