package jp.speakbuddy.image.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStoreImage: DataStore<Preferences> by preferencesDataStore(name = "settings_image")

val IMAGE_DATA = stringPreferencesKey("image_data")
fun getImageData(store: DataStore<Preferences>): Flow<String> {
    return store.data
        .map { preferences ->
            // No type safety.
            preferences[IMAGE_DATA] ?: ""
        }
}

suspend fun saveImageData(store: DataStore<Preferences>, images: String) {
    store.edit { settings ->
        settings[IMAGE_DATA] = images
    }
}