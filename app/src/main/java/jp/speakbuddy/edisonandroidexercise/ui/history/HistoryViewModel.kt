package jp.speakbuddy.edisonandroidexercise.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.speakbuddy.core.common.base.DataState
import jp.speakbuddy.core.ui.state.UIState
import jp.speakbuddy.fact.domain.GetHistory
import jp.speakbuddy.fact.network.FactResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistory: GetHistory
) : ViewModel() {

    private val _factHistory = MutableStateFlow<UIState<List<FactResponse>>>(UIState.Loading)
    val factHistory: StateFlow<UIState<List<FactResponse>>> = _factHistory

    fun getHistoryData(filter: String) {
        getHistory(filter, viewModelScope) {
            when(it) {
                is DataState.Loading -> _factHistory.value = UIState.Loading
                is DataState.Success -> _factHistory.value = UIState.Success(it.result)
                is DataState.Failure -> _factHistory.value = UIState.Error(it.message)
            }
        }
    }
}