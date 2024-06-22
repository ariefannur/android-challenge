package jp.speakbuddy.edisonandroidexercise.ui.fact

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.speakbuddy.core.common.base.BaseUseCase
import jp.speakbuddy.core.common.base.DataState
import jp.speakbuddy.core.ui.state.UIState
import jp.speakbuddy.fact.domain.GetRandomFact
import jp.speakbuddy.fact.domain.model.FactParam
import jp.speakbuddy.fact.network.FactResponse
import jp.speakbuddy.image.domain.GetRandomImage
import jp.speakbuddy.image.network.Photos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FactViewModel @Inject constructor(
    private val getRandomFact: GetRandomFact,
    private val getRandomImage: GetRandomImage
) : ViewModel() {

    private val _imageResponse = MutableStateFlow<UIState<Photos>>(UIState.Loading)
    val imageData: StateFlow<UIState<Photos>> = _imageResponse
    fun getRandomImage() {
        getRandomImage(BaseUseCase.None(), viewModelScope) {
            when(it) {
                is DataState.Loading -> _imageResponse.value = UIState.Loading
                is DataState.Success -> _imageResponse.value = UIState.Success(
                    it.result
                )
                is DataState.Failure -> {
                    Log.d("AF", "image error : ${it.message}")
                    _imageResponse.value = UIState.Error(it.message)
                }
            }
        }
    }

    private val _factResponse = MutableStateFlow<UIState<FactResponse>>(UIState.Loading)
    val factData: StateFlow<UIState<FactResponse>> = _factResponse
    fun getRandomFact(param: FactParam) {
        getRandomFact(param, viewModelScope) {
            when(it) {
                is DataState.Loading -> _factResponse.value = UIState.Loading
                is DataState.Success -> _factResponse.value = UIState.Success(
                    it.result
                )
                is DataState.Failure -> {
                    Log.d("AF", "fact error ${it.message}")
                    _factResponse.value = UIState.Error(it.message)
                }
            }
        }
    }

}
