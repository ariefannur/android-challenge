package jp.speakbuddy.fact.domain

import jp.speakbuddy.core.common.base.BaseUseCase
import jp.speakbuddy.fact.domain.model.FactParam
import jp.speakbuddy.fact.network.FactResponse
import jp.speakbuddy.fact.repository.FactRepository
import kotlinx.coroutines.flow.Flow

class GetRandomFact(
    private val factRepository: FactRepository
): BaseUseCase<FactResponse, FactParam>() {
    override suspend fun run(params: FactParam): Flow<FactResponse> {
        return factRepository.getRandomFact(params.type, params.limit)
    }
}