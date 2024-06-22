package jp.speakbuddy.edisonandroidexercise

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import jp.speakbuddy.core.ui.theme.EdisonAndroidExerciseTheme
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactScreen
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactViewModel
import jp.speakbuddy.edisonandroidexercise.ui.history.HistoryScreen
import jp.speakbuddy.edisonandroidexercise.utils.shareFact
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val context = LocalContext.current
            val viewModel: FactViewModel = hiltViewModel()
            var currentFact by remember { mutableStateOf("") }

            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            var menuSelected by remember { mutableStateOf("fact") }

            EdisonAndroidExerciseTheme {
                // A surface container using the 'background' color from the theme
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet {
                            DropdownMenuItem(
                                modifier = Modifier.padding(top = 24.dp),
                                leadingIcon = {
                                    Icon(Icons.Filled.Home , contentDescription = "")
                                },
                                text = {
                                    Text(text = stringResource(R.string.menu_fact), style = MaterialTheme.typography.titleMedium)
                                },
                                onClick = {
                                    menuSelected = "fact"
                                    scope.launch {
                                        drawerState.close()
                                    }
                                }
                            )
                            DropdownMenuItem(
                                modifier = Modifier.padding(top = 8.dp),
                                leadingIcon = {
                                    Icon(Icons.Filled.CheckCircle , contentDescription = "")
                                },
                                text = {
                                    Text(text = stringResource(R.string.menu_history), style = MaterialTheme.typography.titleMedium)
                                },
                                onClick = {
                                    menuSelected = "history"
                                    scope.launch {
                                        drawerState.close()
                                    }
                                }
                            )

                        }
                    },
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = if (menuSelected == "fact") {
                                            stringResource(R.string.title_fact_cat_app)
                                        } else {
                                            stringResource(R.string.menu_history)
                                        }
                                    )
                                },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        scope.launch {
                                            drawerState.apply {
                                                if (isClosed) open() else close()
                                            }
                                        }
                                    }) {
                                        Icon(Icons.Default.Menu, contentDescription = "")
                                    }
                                },
                                actions = {
                                    if (menuSelected == "fact") {
                                        IconButton(onClick = {
                                            shareFact(context, currentFact)
                                        }) {
                                            Icon(Icons.Default.Share, contentDescription = "")
                                        }
                                    }

                                }
                            )
                        },
                        contentWindowInsets = WindowInsets.navigationBars
                    ) {
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            when(menuSelected) {
                                "fact" -> FactScreen(viewModel = viewModel) { fact ->
                                    currentFact = fact
                                }
                                "history" -> HistoryScreen()
                            }
                        }
                    }

                }
            }
        }
    }
}