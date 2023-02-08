package com.meanwhile.cancellationtest

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

class GetSomethingUseCase(private val repo: SomethingRepository) {

    operator fun invoke() = repo.getSomethingFlow()
        .onStart { println("useCase: onStart") }
        .map { "$it" }
        .onCompletion { println("useCase onCompletion") }
}