package com.meanwhile.cancellationtest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel: ViewModel() {

    private val repo = SomethingRepository()
    private val getSomethingUseCase = GetSomethingUseCase(repo)

    val uiState: StateFlow<UiState> = getSomethingUseCase()
        .map<String, UiState> {
            UiState.Success(it)
        }.catch {
            emit(UiState.Error(it))
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), UiState.Success("init"))
}

sealed class UiState {
    class Success(val text: String): UiState()
    class Error(val ex: Throwable): UiState()
}