package com.promethis.openai_chatter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.stereotype.Service
import dev.langchain4j.model.chat.ChatLanguageModel
import dev.langchain4j.model.openai.OpenAiChatModel
import dev.langchain4j.model.openai.OpenAiChatModelName.GPT_3_5_TURBO

data class Question(val name: String, val question: String)

@Service
class AIManager {
    //    private val apiKey = (System.getenv("OPENAI_API_KEY")
    //        ?: "").ifEmpty { throw IllegalArgumentException("env var OPENAI_API_KEY is not set") }
    private val apiKey = "demo"
    private val model: ChatLanguageModel = OpenAiChatModel.builder()
        .apiKey(apiKey)
        .modelName(GPT_3_5_TURBO)
        .maxTokens(50)
        .build();

    fun getResponse(question: Question): String =
        model.generate("Greet the user ${question.name} and answer his question: \"${question.question}\"")
}

@RestController
class MessageController(val aiManager: AIManager) {
    @GetMapping("/chat")
    fun index(@RequestParam("name") name: String = "", @RequestParam("question") question: String = ""): String {
        if (name.isEmpty() || name.isBlank()) {
            return "`question` was not set"
        }
        if (question.isEmpty() || question.isBlank()) {
            return "`text` was not set"
        }

        return aiManager.getResponse(Question(name, question))
    }
}

@SpringBootApplication
class OpenaiChatterApplication

fun main(args: Array<String>) {
    runApplication<OpenaiChatterApplication>(*args)
}
