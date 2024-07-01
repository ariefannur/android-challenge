package jp.speakbuddy.image.domain

import jp.speakbuddy.core.common.base.BaseUseCase
import jp.speakbuddy.image.network.Photos
import jp.speakbuddy.image.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetRandomImage (
    private val imageRepository: ImageRepository
) : BaseUseCase<Photos, BaseUseCase.None>() {
    override suspend fun run(params: None): Flow<Photos> {
        return imageRepository.getRandomImage()
    }

}