package jp.speakbuddy.fact

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jp.speakbuddy.fact.datasource.FactLocal
import jp.speakbuddy.fact.datasource.FactLocalDataSource
import jp.speakbuddy.fact.datasource.FactRemote
import jp.speakbuddy.fact.datasource.FactRemoteDataSource
import jp.speakbuddy.fact.domain.model.Fetching
import jp.speakbuddy.fact.network.FactResponse
import jp.speakbuddy.fact.repository.FactRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import kotlin.random.Random

class FactRepositoryTest {
    private val local: FactLocalDataSource = FactLocal(mockk(relaxed = true))
    private val remote: FactRemoteDataSource = FactRemote(mockk(relaxed = true))
    private val repository = FactRepository(local, remote)

    @Test
    fun `getRandomFact should got local data source when state under test`()  = runTest {
//        given
        val fetching = Fetching.LOCAL
//        when
        val response = mutableListOf<FactResponse>()
        repository.getRandomFact(fetching).toList(response)
//        than
        Assert.assertEquals(response.first(), FactResponse("", 0))
    }

    @Test
    fun `getRandomFact should got remote data source when state under test`() = runTest {
//        given
        val sampleResponse = FactResponse("sample fact response", 100)
        val fetching = Fetching.REMOTE
        coEvery { remote.fetchFact() } returns sampleResponse
//        when
        val response = mutableListOf<FactResponse>()
        repository.getRandomFact(fetching).toList(response)
//        than
        Assert.assertEquals(response.first(), sampleResponse)
    }

    @Test
    fun `getRandomFact should got local data then update remote data source when state under test`() = runTest {
//        given
        val localResponse = FactResponse("sample fact from local", 100)
        val remoteResponse = FactResponse("sample fact from remote", 110)
        val fetching = Fetching.LOCAL_AN_UPDATE
        coEvery { local.getLastFact() } returns localResponse
        coEvery { remote.fetchFact() } returns remoteResponse
//        when
        val response = mutableListOf<FactResponse>()
        repository.getRandomFact(fetching).toList(response)
//        than
        Assert.assertEquals(response.first(), localResponse)
        Assert.assertEquals(response[1], remoteResponse)
    }

    @Test
    fun `getRandomFact should got remote data source with length greater than 100 when state under test`()  = runTest {
//        given
        val sampleResponse = FactResponse("sample fact response", 90)
        val sampleResponse2 = FactResponse("sample fact response", 105)
        val fetching = Fetching.REMOTE
        coEvery { remote.fetchFact() } returnsMany listOf(sampleResponse, sampleResponse2)
//        when
        val response = mutableListOf<FactResponse>()
        repository.getRandomFact(fetching, 100).toList(response)
//        than
        Assert.assertEquals(response.first(), sampleResponse2)
    }

}