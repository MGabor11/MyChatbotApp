package com.marossolutions.mychatbotapp.model.domain

sealed interface AnswerState {
    object Init : AnswerState
    object Loading : AnswerState
    object Error : AnswerState
    data class Success(val answer: AIChatAnswer) : AnswerState
}