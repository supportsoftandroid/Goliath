package com.fantasy.goliath.utility

import kotlinx.coroutines.*

class Debouncer(private val timeMillis: Long = 500) {
    private var debounceJob: Job? = null

    fun debounce(action: () -> Unit) {
        debounceJob?.cancel()
        debounceJob = CoroutineScope(Dispatchers.Main).launch {
            delay(timeMillis)
            action()
        }
    }
}