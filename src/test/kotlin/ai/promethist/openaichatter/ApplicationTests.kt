package ai.promethist.openaichatter

import ai.promethist.openaichatter.model.Query
import ai.promethist.openaichatter.service.ChatDatabase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import java.net.URI


@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
)
internal class ApplicationTests {
    @LocalServerPort
    val serverPort: Int = 0

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Autowired
    lateinit var chatDatabase: ChatDatabase

    private fun applicationUrl() = "http://localhost:$serverPort"

    @Test
    fun queryConstructor() {
        Query("Tonda", "Jak se máš?").let {
            Assertions.assertEquals("Tonda", it.name)
            Assertions.assertEquals("Jak se máš?", it.text)
            Assertions.assertEquals(null, it.sessionId)
        }
    }

    @Test
    fun chatDatabaseEntryInitialization() {
        val id1 = 31145653546
        val id2 = 846356438743646

        chatDatabase.getChatHistory(id1).let { (memory, newlyCreated) ->
            Assertions.assertNotNull(memory)
            Assertions.assertTrue(newlyCreated)
        }
        chatDatabase.getChatHistory(id1).let { (memory, newlyCreated) ->
            Assertions.assertNotNull(memory)
            Assertions.assertFalse(newlyCreated)
        }
        chatDatabase.getChatHistory(id2).let { (memory, newlyCreated) ->
            Assertions.assertNotNull(memory)
            Assertions.assertTrue(newlyCreated)
        }
        chatDatabase.getChatHistory(id1).let { (memory, newlyCreated) ->
            Assertions.assertNotNull(memory)
            Assertions.assertFalse(newlyCreated)
        }
        chatDatabase.getChatHistory(id2).let { (memory, newlyCreated) ->
            Assertions.assertNotNull(memory)
            Assertions.assertFalse(newlyCreated)
        }
    }

    @Test
    fun badRequest() {
        Assertions.assertEquals(
            HttpStatus.BAD_REQUEST, testRestTemplate.getForEntity(
                URI(applicationUrl() + "/chat"), String::class.java
            ).statusCode
        )
        Assertions.assertEquals(
            HttpStatus.BAD_REQUEST, testRestTemplate.getForEntity(
                URI(applicationUrl() + "/chat?name=NameTest"), String::class.java
            ).statusCode
        )
        Assertions.assertEquals(
            HttpStatus.BAD_REQUEST, testRestTemplate.getForEntity(
                URI(applicationUrl() + "/chat?text=TextTest"), String::class.java
            ).statusCode
        )
        Assertions.assertEquals(
            HttpStatus.BAD_REQUEST, testRestTemplate.getForEntity(
                URI(applicationUrl() + "/chat?text=NameTest&name="), String::class.java,
            ).statusCode
        )
        Assertions.assertEquals(
            HttpStatus.BAD_REQUEST, testRestTemplate.getForEntity(
                URI(applicationUrl() + "/chat?sessionId=10"), String::class.java
            ).statusCode
        )
        Assertions.assertEquals(
            HttpStatus.BAD_REQUEST, testRestTemplate.getForEntity(
                URI(applicationUrl() + "/chat?name=NameTest&text=TextTest&sessionId=Ahojky"), String::class.java
            ).statusCode
        )
    }

    @Test
    fun correctRequest() {
        Assertions.assertEquals(
            HttpStatus.OK, testRestTemplate.getForEntity(
                URI(applicationUrl() + "/chat?name=NameTest&text=TextTest&"), String::class.java
            ).statusCode
        )

        Assertions.assertEquals(
            HttpStatus.OK, testRestTemplate.getForEntity(
                URI(applicationUrl() + "/chat?name=NameTest&text=TextTest&@sessionId=314159"), String::class.java
            ).statusCode
        )
    }
}
