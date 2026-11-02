package com.example.linknest.ui.theme

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.linkNestDataStore by preferencesDataStore(name = "linknest_preferences")
private val THEME_MODE_KEY = stringPreferencesKey("theme_mode")

object ThemePreferences {
    const val SYSTEM = "system"
    const val LIGHT = "light"
    const val DARK = "dark"

    fun themeMode(context: Context): Flow<String> =
        context.linkNestDataStore.data.map { preferences ->
            preferences[THEME_MODE_KEY] ?: SYSTEM
        }

    suspend fun setThemeMode(context: Context, mode: String) {
        context.linkNestDataStore.edit { preferences ->
            preferences[THEME_MODE_KEY] = mode
        }
    }
}
