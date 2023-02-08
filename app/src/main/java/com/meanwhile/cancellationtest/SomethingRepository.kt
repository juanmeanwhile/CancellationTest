package com.meanwhile.cancellationtest

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SomethingRepository {

    private var count = 0
    private lateinit var job: Job

    fun getSomethingFlow(): Flow<Int> = flow {
        emit(-1)
        while (true){
            val num = getSomething()
            emit(num)
        }
    }

    suspend fun getSomething(): Int {
        withContext(Dispatchers.Default) {
            job = launch {
                try {
                    delay(1000)
                    count++
                } catch(e: Exception) {
                    println("repo - catch: $e")
                    throw mapToMyException(e)
                }
            }
        }

        return count
    }

    private fun mapToMyException(e: Throwable): Throwable {
        return when (e) {
            //is CancellationException -> e // Uncomment to fixme
            else -> WrapperException()
        }
    }
}

class WrapperException(): Throwable()