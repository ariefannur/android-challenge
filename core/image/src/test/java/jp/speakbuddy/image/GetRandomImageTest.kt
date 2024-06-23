package jp.speakbuddy.image

import io.mockk.coEvery
import io.mockk.mockk
import jp.speakbuddy.image.datasource.ImageLocalDataSource
import jp.speakbuddy.image.datasource.ImageRemoteDataSource
import jp.speakbuddy.image.network.Photos
import jp.speakbuddy.image.network.Src
import jp.speakbuddy.image.repository.ImageRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class GetRandomImageTest {

    private val local: ImageLocalDataSource = mockk(relaxed = true)
    private val remote: ImageRemoteDataSource = mockk(relaxed = true)
    private val repository = ImageRepository(local, remote)

    @Test
    fun `getRandomImage should get data from remote when state under test`() = runTest {
//        given
        val photo = Photos(Src("medium-image", "large-image", "small-image"))
        coEvery { local.getImage() } returns flowOf(listOf())
        coEvery { remote.fetchImage() } returns listOf(photo)
//        when
        val photos = mutableListOf<Photos>()
        repository.getRandomImage().toList(photos)
//        than
        Assert.assertEquals(photos.first(), photo)

    }

    @Test
    fun `getRandomImage should get data from local when state under test`() = runTest {
//        given
        val photo = Photos(Src("medium-image", "large-image", "small-image"))
        coEvery { local.getImage() } returns flowOf(listOf(photo))
        coEvery { remote.fetchImage() } returns listOf()
//        when
        val photos = mutableListOf<Photos>()
        repository.getRandomImage().toList(photos)
//        than
        Assert.assertEquals(photos.first(), photo)
    }
}