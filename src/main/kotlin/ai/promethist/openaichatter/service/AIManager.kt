package ai.promethist.openaichatter.service

import org.springframework.stereotype.Service
import dev.langchain4j.model.chat.ChatLanguageModel
import dev.langchain4j.model.openai.OpenAiChatModel
import dev.langchain4j.model.openai.OpenAiChatModelName.GPT_3_5_TURBO

@Service
class AIManager {
    private val apiKey = (System.getenv("OPENAI_API_KEY") ?: "demo")
    private val model: ChatLanguageModel = OpenAiChatModel.builder()
        .apiKey(apiKey)
        .modelName(GPT_3_5_TURBO)
        .maxTokens(50)
        .build();

    fun getResponse(question: Question): String =
        model.generate("Greet the user ${question.name} and answer his question: \"${question.question}\"")
}
