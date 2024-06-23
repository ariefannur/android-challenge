package jp.speakbuddy.edisonandroidexercise.ui

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import jp.speakbuddy.core.common.base.BaseUseCase
import jp.speakbuddy.core.ui.state.UIState
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactViewModel
import jp.speakbuddy.fact.domain.GetRandomFact
import jp.speakbuddy.fact.domain.model.FactParam
import jp.speakbuddy.fact.domain.model.Fetching
import jp.speakbuddy.fact.network.FactResponse
import jp.speakbuddy.image.domain.GetRandomImage
import jp.speakbuddy.image.network.Photos
import jp.speakbuddy.image.network.Src
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class FactViewModelTest {

    private val getRandomFact = GetRandomFact(mockk(relaxed = true))
    private val getRandomImage = GetRandomImage(mockk(relaxed = true))
    private val viewModel = FactViewModel(getRandomFact, getRandomImage)

    @Test
    fun `getRandomFact should got fact response when state under state`() = runTest {
//        given
        val response = FactResponse("This is sample fact response", 110)
        val param = FactParam(Fetching.REMOTE, 100)

        coEvery {
            getRandomFact.run(param)
        } returns flow {
            delay(200)
            emit(response)
        }
//        when
        viewModel.getRandomFact(param)
//        than
        viewModel.factData.test {
            assertEquals(UIState.Loading, awaitItem())
            assertEquals(UIState.Success(response), awaitItem())
        }


    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getRandomImage should got photos response when state under state`() = runTest {
//        given
        val response = Photos(Src("image-medium", "image-large", "image-small"))
        coEvery {
            getRandomImage.run(BaseUseCase.None())
        } returns flow {
            delay(200)
            emit(response)
        }
//        when
        viewModel.getRandomImage()
//        than
        viewModel.imageData.test {
            assertEquals(UIState.Loading, awaitItem())
            assertEquals(UIState.Success(response), awaitItem())
        }


    }
}
