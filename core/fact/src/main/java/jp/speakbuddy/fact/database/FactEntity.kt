package jp.speakbuddy.fact.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import jp.speakbuddy.fact.network.FactResponse

@Entity
data class FactEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val fact: String,
    val length: Int
) {
    fun toFactResponse(): FactResponse {
        return FactResponse(
            fact, length
        )
    }
}