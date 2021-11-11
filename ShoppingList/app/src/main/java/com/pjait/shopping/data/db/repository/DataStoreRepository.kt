package com.pjait.shopping.data.db.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.pjait.shopping.data.db.entity.UserSettings
import kotlinx.coroutines.flow.map


class DataStoreRepository(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
        val DARK_MODE = stringPreferencesKey("DARK_MODE")
        val TEXT_SIZE = stringPreferencesKey("TEXT_SIZE")
    }

    suspend fun save(userSettings: UserSettings){
        context.dataStore.edit { settings ->
            settings[DARK_MODE] = userSettings.isDarkModeEnabled
            settings[TEXT_SIZE] = userSettings.textSize
        }
    }

    fun read() = context.dataStore.data.map {
         UserSettings(
            isDarkModeEnabled = it[DARK_MODE]?:"",
            textSize = it[TEXT_SIZE]?:""
        )
    }
}