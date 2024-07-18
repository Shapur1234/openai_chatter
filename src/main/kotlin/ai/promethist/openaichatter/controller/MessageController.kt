package ai.promethist.openaichatter.controller

import ai.promethist.openaichatter.service.AIManager
import ai.promethist.openaichatter.model.Question

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageController(private val aiManager: AIManager) {
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
