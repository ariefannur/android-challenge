package jp.speakbuddy.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun LoadingSection(modifier: Modifier = Modifier, row: Int, height: Int = 24) {
    Column (
        modifier
            .fillMaxWidth()
            .shimmer()){
        repeat(row) {
            Spacer(modifier = Modifier
                .padding(bottom = 16.dp)
                .height(height.dp)
                .fillMaxWidth(1f)
                .background(MaterialTheme.colorScheme.onBackground))
        }
    }
}