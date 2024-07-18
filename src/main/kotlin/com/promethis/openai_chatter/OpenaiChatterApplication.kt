package com.promethis.openai_chatter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

data class Message(val name: String, val text: String)

@RestController
class MessageController {
    @GetMapping("/chat")
    fun index(@RequestParam("name") name: String = "", @RequestParam("text") text: String = "") : String {
        if (name.isBlank()) {
            return "`text` was not set"
        }
        if (text.isBlank()) {
            return "`text` was not set"
        }

        return "${Message(name, name)}"
    }
}

@SpringBootApplication
class OpenaiChatterApplication

fun main(args: Array<String>) {
//    val apiKey = (System.getenv("OPENAI_API_KEY")
//        ?: "").ifEmpty { throw IllegalArgumentException("env var OPENAI_API_KEY is not set") }
    val apiKey = "demo"

    runApplication<OpenaiChatterApplication>(*args)
}
