package ru.yandex.praktikumchatapp.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.retryWhen
import kotlin.math.pow

class ChatRepository(
    private val api: ChatApi = ChatApi()
) {

    private var currentDelay = 1000L

    fun getReplyMessage(): Flow<String> {
        return api.getReply()
            .retryWhen { _, attempt ->
                delay(currentDelay)
                currentDelay *= (2.0).pow(attempt.toDouble()).toLong() // Экспоненциальное увеличение
                true
            }
    }
}