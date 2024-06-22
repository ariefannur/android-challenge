package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.imageLoader
import jp.speakbuddy.core.ui.components.LoadingSection
import jp.speakbuddy.core.ui.state.UIState
import jp.speakbuddy.core.ui.theme.EdisonAndroidExerciseTheme
import jp.speakbuddy.edisonandroidexercise.R
import jp.speakbuddy.fact.domain.model.FactParam
import jp.speakbuddy.fact.domain.model.Fetching
import jp.speakbuddy.fact.network.FactResponse
import jp.speakbuddy.image.network.Photos

@Composable
fun FactScreen(
    viewModel: FactViewModel,
    currentFact: (String) -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.getRandomImage()
        viewModel.getRandomFact(FactParam(Fetching.LOCAL, 100))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        val context = LocalContext.current
        val uiStateImage by viewModel.imageData.collectAsStateWithLifecycle()

        if (uiStateImage is UIState.Loading) {
            LoadingSection(modifier = Modifier.padding(16.dp), row = 1, height = 250)
        } else if (uiStateImage is UIState.Success) {
            val image = (uiStateImage as UIState.Success<Photos>).result.src.medium
            AsyncImage(
                model = image,
                contentDescription = "img",
                contentScale = ContentScale.FillBounds,
                imageLoader = context.imageLoader,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f)
                    .zIndex(0f)
                    .graphicsLayer {
                        shadowElevation = 4.dp.toPx()
                        clip = true
                    }
            )
        }
        
        Column(
            modifier = Modifier
                .weight(3f)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterVertically
            )
        ) {

            val uiState by viewModel.factData.collectAsStateWithLifecycle()
            if (uiState is UIState.Loading) {
                LoadingSection(modifier = Modifier.padding(16.dp), row = 3)
            } else if (uiState is UIState.Success) {
                val result = (uiState as UIState.Success<FactResponse>).result
                currentFact.invoke(result.fact)
                Text(
                    text = stringResource(R.string.title_cat_fact_today),
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = result.fact,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                val onClick = {
                    viewModel.getRandomFact(FactParam(Fetching.REMOTE, 100))
                }

                Button(onClick = onClick) {
                    Text(text = stringResource(R.string.update_button))
                }
            }
        }
    }
}

@Preview
@Composable
private fun FactScreenPreview() {
    val viewModel: FactViewModel = hiltViewModel()
    EdisonAndroidExerciseTheme {
        FactScreen(viewModel = viewModel) {

        }
    }
}
