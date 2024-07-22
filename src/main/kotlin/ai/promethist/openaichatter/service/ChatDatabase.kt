package ai.promethist.openaichatter.service

import dev.langchain4j.memory.ChatMemory
import dev.langchain4j.memory.chat.MessageWindowChatMemory
import org.springframework.stereotype.Service

@Service
class ChatDatabase {
    final val maxStoredMessages: Int = 10

    private val database: MutableMap<Long, ChatMemory> = mutableMapOf()

    // If boolean is true, history was newly initialized
    fun getChatHistory(sessionId: Long): Pair<ChatMemory, Boolean> {
        if (database[sessionId] == null) {
            database[sessionId] = blankChatMemory(sessionId)
            return database[sessionId]!! to true
        } else {
            return database[sessionId]!! to false
        }
    }

    private fun blankChatMemory(sessionId: Long): ChatMemory =
        MessageWindowChatMemory.builder().id(sessionId).maxMessages(maxStoredMessages).build()
}
