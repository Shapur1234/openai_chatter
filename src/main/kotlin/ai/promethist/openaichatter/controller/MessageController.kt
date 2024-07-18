package ai.promethist.openaichatter.controller

import ai.promethist.openaichatter.service.AIManager
import ai.promethist.openaichatter.model.Query

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageController(private val aiManager: AIManager) {
    @GetMapping("/chat")
    fun index(
        @RequestParam("name") name: String = "",
        @RequestParam("text") text: String = "",
        @RequestParam("sessionId") sessionId: Long? = null
    ): Any {
        if (name.isEmpty() || name.isBlank()) {
            return "`name` was not set"
        }
        if (text.isEmpty() || text.isBlank()) {
            return "`text` was not set"
        }

        return aiManager.getResponse(Query(name, text, sessionId))
    }
}
