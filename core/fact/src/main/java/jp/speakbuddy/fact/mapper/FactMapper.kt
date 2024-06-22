package jp.speakbuddy.fact.mapper

import jp.speakbuddy.fact.database.FactEntity
import jp.speakbuddy.fact.network.FactResponse

fun FactResponse.toFactEntity(): FactEntity {
    return FactEntity(null, fact, length)
}