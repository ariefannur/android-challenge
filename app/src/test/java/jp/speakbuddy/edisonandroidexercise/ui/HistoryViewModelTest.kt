package jp.speakbuddy.edisonandroidexercise.ui

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import jp.speakbuddy.core.ui.state.UIState
import jp.speakbuddy.edisonandroidexercise.ui.history.HistoryViewModel
import jp.speakbuddy.fact.domain.GetHistory
import jp.speakbuddy.fact.network.FactResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class HistoryViewModelTest {
    private val getHistory = GetHistory(mockk(relaxed = true))
    private val viewModel = HistoryViewModel(getHistory)

    @Test
    fun `get history should got data list history when state under test`() = runTest {
//        given
        val response = listOf(
            FactResponse("Response Fact 1", 100),
            FactResponse("Response Fact 2", 90),
            FactResponse("Response Fact 3", 95),
        )
        coEvery { getHistory.run("") } returns flow {
            delay(200)
            emit(response)
        }
//        when
        viewModel.getHistoryData("")
//        then
        viewModel.factHistory.test {
            Assert.assertEquals(UIState.Loading, awaitItem())
            Assert.assertEquals(UIState.Success(response), awaitItem())
        }
    }

    @Test
    fun `get history and search it should got data list history when state under test`() = runTest {
//        given
        val response = listOf(
            FactResponse("Response Fact 1", 100),
        )
        coEvery { getHistory.run("fact") } returns flow {
            delay(200)
            emit(response)
        }
//        when
        viewModel.getHistoryData("fact")
//        then
        viewModel.factHistory.test {
            Assert.assertEquals(UIState.Loading, awaitItem())
            Assert.assertEquals(UIState.Success(response), awaitItem())
        }
    }
}