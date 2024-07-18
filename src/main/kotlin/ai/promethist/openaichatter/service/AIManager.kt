package ai.promethist.openaichatter.service

import ai.promethist.openaichatter.model.Query

import org.springframework.stereotype.Service
import dev.langchain4j.model.chat.ChatLanguageModel
import dev.langchain4j.model.openai.OpenAiChatModel
import dev.langchain4j.model.openai.OpenAiChatModelName.GPT_3_5_TURBO
import dev.langchain4j.data.message.UserMessage.userMessage

@Service
class AIManager(private val chatDatabase: ChatDatabase) {
    val maxTokens: Int = 50

    private val apiKey = (System.getenv("OPENAI_API_KEY") ?: "demo")
    private val model: ChatLanguageModel = OpenAiChatModel.builder()
        .apiKey(apiKey)
        .modelName(GPT_3_5_TURBO)
        .maxTokens(maxTokens)
        .build();

    fun getResponse(query: Query): String {
        if (query.sessionId == null) {
            return model.generate(initialMessage(query))
        } else {
            val (memory, newHistory) = chatDatabase.getChatHistory(query.sessionId)
            if (newHistory) {
                memory.add(userMessage(initialMessage(query)))
            } else {
                memory.add(userMessage(query.text))
            }

            val answer = model.generate(memory.messages()).content();
            memory.add(answer)

            return answer.text()
        }
    }

    fun initialMessage(query: Query): String = "Greet the user ${query.name} and answer their query: \"${query.text}\""
}
