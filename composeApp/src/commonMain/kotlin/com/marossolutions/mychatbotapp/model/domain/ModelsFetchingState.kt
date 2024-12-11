package com.marossolutions.mychatbotapp.model.domain

sealed interface ModelsFetchingState {
    data object Init : ModelsFetchingState
    data object Loading : ModelsFetchingState
    data object Error : ModelsFetchingState
    data class Success(val models: List<OpenAIModel>) : ModelsFetchingState
}