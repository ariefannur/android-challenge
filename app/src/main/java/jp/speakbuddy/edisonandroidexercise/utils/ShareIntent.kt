package jp.speakbuddy.edisonandroidexercise.utils

import android.content.Context
import android.content.Intent

fun shareFact(context: Context, message: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, message)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}