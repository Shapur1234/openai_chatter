package com.promethis.openai_chatter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OpenaiChatterApplication

fun main(args: Array<String>) {
    val apiKey = (System.getenv("OPENAI_API_KEY")
        ?: "").ifEmpty { throw IllegalArgumentException("env var OPENAI_API_KEY is not set") }

    runApplication<OpenaiChatterApplication>(*args)
}
