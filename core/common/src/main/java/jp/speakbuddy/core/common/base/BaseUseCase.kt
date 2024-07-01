package jp.speakbuddy.core.common.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class BaseUseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Flow<Type>

    operator fun invoke(
        params: Params,
        scope: CoroutineScope = MainScope(),
        callback: (DataState<Type>) -> Unit = {}
    ) {
        scope.launch(Dispatchers.IO) {
            try {
                callback.invoke(DataState.Loading)
                val deferred = async {  run(params) }
                deferred.await().collect {
                    callback.invoke(DataState.Success(it))
                }
            } catch (e: Exception) {
                println("Error ${e.message}")
                callback.invoke(DataState.Failure(404, e.message ?: ""))
            }
        }
    }

    class None
}