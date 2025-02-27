package ru.yandex.praktikumchatapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.yandex.praktikumchatapp.presentation.ChatViewModel
import ru.yandex.praktikumchatapp.presentation.Message

@ExperimentalCoroutinesApi
class ChatViewModelTest {

    private var testDispatcher: TestDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: ChatViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ChatViewModel(isWithReplies = false)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `send message should update messages with MyMessage`() = runTest {
        val message = Message.MyMessage("TestMessage")
        val messageText = message.text
        viewModel.sendMyMessage(messageText)

        val messagesList = viewModel.messages.value
        assert(messagesList.contains(message)) { "Сообщение не было добавлено в список сообщений" }
    }

    @Test
    fun testReceiveMessage_concurrentMessages() = runTest {
        val messagesToSend = (1..100).map { Message.MyMessage("Message $it") }
        val jobs = mutableListOf<Job>()

        coroutineScope {
            messagesToSend.forEach { message ->
                val job = launch {
                    viewModel.sendMyMessage(message.text)
                }
                jobs.add(job)
            }
            jobs.joinAll()
        }

        val messagesList = viewModel.messages.value
        assert(messagesList.size == messagesToSend.size) { "Количество сообщений не совпадает" }
        assert(messagesList.containsAll(messagesToSend)) { "Не все сообщения были добавлены в список сообщений" }
    }
}