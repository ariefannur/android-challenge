package jp.speakbuddy.edisonandroidexercise.ui.history

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.speakbuddy.core.ui.components.LoadingSection
import jp.speakbuddy.core.ui.state.UIState
import jp.speakbuddy.fact.network.FactResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HistoryScreen(modifier: Modifier = Modifier, viewModel: HistoryViewModel = hiltViewModel()) {

    LaunchedEffect(viewModel) {
        viewModel.getHistoryData("")
    }

    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        var searchText by remember { mutableStateOf("") }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchText,
            onValueChange = {
                searchText = it
                scope.launch {
                    delay(200)
                    viewModel.getHistoryData(searchText)
                }
            },
            label = {
                Text("Search")
            }
        )
        val uiState by viewModel.factHistory.collectAsStateWithLifecycle()

        if (uiState is UIState.Loading) {
            LoadingSection(modifier = Modifier.padding(top = 16.dp), row = 5, height = 30)
        } else if (uiState is UIState.Success) {
            val listData = (uiState as UIState.Success<List<FactResponse>>).result
            LazyColumn (modifier = Modifier.padding(top = 16.dp)) {
                items(listData) {
                    FlowRow (
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 8.dp)
                            .fillMaxWidth()
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.secondary,
                                MaterialTheme.shapes.medium
                            )
                            .padding(12.dp)
                    ) {
                        Text(text = it.fact, style = MaterialTheme.typography.titleMedium)
                        Text(text = "(${it.length})", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }

    }
}