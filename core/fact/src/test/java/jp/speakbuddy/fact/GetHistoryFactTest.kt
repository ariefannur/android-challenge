package jp.speakbuddy.fact

import io.mockk.every
import io.mockk.mockk
import jp.speakbuddy.fact.domain.GetHistory
import jp.speakbuddy.fact.network.FactResponse
import jp.speakbuddy.fact.repository.FactRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class GetHistoryFactTest {

    private val repository: FactRepository = mockk(relaxed = true)
    private val getHistory = GetHistory(repository)

    @Test
    fun `getHistory use case should got list fact history when state under test`() = runTest {
//        given
        val sampleResponse = listOf(
            FactResponse("fact 1", 10),
            FactResponse("fact 2", 10),
            FactResponse("fact 3", 10)
        )
        every { repository.getListHistory("") } returns flowOf(
            sampleResponse
        )
//        when
        val resultData = mutableListOf<List<FactResponse>>()

        val result = getHistory.run("")
        result.toList(resultData)
//        than
        Assert.assertEquals(sampleResponse, resultData.first())
    }

}