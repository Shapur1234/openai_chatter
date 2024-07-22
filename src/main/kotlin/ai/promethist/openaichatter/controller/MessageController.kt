package ai.promethist.openaichatter.controller

import ai.promethist.openaichatter.service.AIManager
import ai.promethist.openaichatter.model.Query

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/chat")
class MessageController(private val aiManager: AIManager) {
    @GetMapping
    fun chat(
        @RequestParam("name") name: String = "",
        @RequestParam("text") text: String = "",
        @RequestParam("sessionId") sessionId: Long? = null
    ): ResponseEntity<Any> {
        if (name.isEmpty() || name.isBlank() || text.isEmpty() || text.isBlank()) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }

        return ResponseEntity.ok(
            aiManager.getResponse(Query(name, text, sessionId))
        )
    }
}
