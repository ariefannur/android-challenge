package jp.speakbuddy.fact.domain

import jp.speakbuddy.core.common.base.BaseUseCase
import jp.speakbuddy.fact.network.FactResponse
import jp.speakbuddy.fact.repository.FactRepository
import kotlinx.coroutines.flow.Flow

class GetHistory(
    private val factRepository: FactRepository
): BaseUseCase<List<FactResponse>, String>() {
    override suspend fun run(params: String): Flow<List<FactResponse>> {
        return factRepository.getListHistory(params)
    }

}