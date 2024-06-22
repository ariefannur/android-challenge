package jp.speakbuddy.core.ui.state

sealed class UIState<out T: Any> {
    data object Loading: UIState<Nothing>()
    data class Success<T: Any>(val result: T): UIState<T>()
    data class Error(val message: String): UIState<Nothing>()
}