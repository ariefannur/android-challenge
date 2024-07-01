package jp.speakbuddy.fact.domain.model

data class FactParam(
    val type: Fetching,
    val limit: Int = 0
)

enum class Fetching {
    LOCAL,
    LOCAL_AN_UPDATE,
    REMOTE
}