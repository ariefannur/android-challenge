package jp.speakbuddy.fact

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import jp.speakbuddy.core.common.base.DataState
import jp.speakbuddy.fact.domain.GetRandomFact
import jp.speakbuddy.fact.domain.model.FactParam
import jp.speakbuddy.fact.domain.model.Fetching
import jp.speakbuddy.fact.network.FactResponse
import jp.speakbuddy.fact.repository.FactRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class GetRandomFactTest {

    private val repository: FactRepository = mockk(relaxed = true)
    private val getRandomFact = GetRandomFact(repository)

    @Test
    fun `getRandomFact should got data fact when state under test`() = runTest {
//        given
        val response = FactResponse("sample 1", 100)
        coEvery { repository.getRandomFact(Fetching.REMOTE, 90) } returns flowOf(response)

//        when
        var result: FactResponse? = null
        getRandomFact(FactParam(Fetching.REMOTE, 90)) {
            when(it) {
                is DataState.Success -> result = it.result
                else -> Unit
            }
        }
//        than
        coVerify { repository.getRandomFact(Fetching.REMOTE, 90) }

        Assert.assertEquals(result, response)
    }

}