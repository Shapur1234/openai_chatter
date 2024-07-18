package ai.promethist.openaichatter.model

data class Query(val name: String, val text: String, val sessionId: Long?) {
    constructor(name: String, text: String) : this(name, text, null)
}
