package kz.aues.photohosting.data.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kz.aues.photohosting.data.account.entities.AccountIdAndNameDataEntity
import javax.inject.Inject

private const val ACCOUNT_PREFERENCES_NAME = "account_preferences"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = ACCOUNT_PREFERENCES_NAME)

class PreferencesDataStoreSettingsDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingsDataSource {

    override suspend fun getUid(): String {
        return context.dataStore.data.map { preferences ->
            preferences[AccountSettingsScheme.KEY_ACCOUNT_UID] ?: DEFAULT_UID_VALUE
        }.first()
    }

    override suspend fun setUid(uid: String?) {
        context.dataStore.edit { preferences ->
            preferences[AccountSettingsScheme.KEY_ACCOUNT_UID] = uid ?: DEFAULT_UID_VALUE
        }
    }

    override suspend fun getAccountName(): String {
        return context.dataStore.data.map { preferences ->
            preferences[AccountSettingsScheme.KEY_ACCOUNT_USERNAME] ?: DEFAULT_USERNAME_VALUE
        }.first()
    }

    override suspend fun setAccountName(username: String?) {
        context.dataStore.edit { preferences ->
            preferences[AccountSettingsScheme.KEY_ACCOUNT_USERNAME] = username ?: DEFAULT_USERNAME_VALUE
        }
    }

    companion object {
        const val DEFAULT_UID_VALUE = "DEFAULT_UID_VALUE"
        const val DEFAULT_USERNAME_VALUE = "DEFAULT_USERNAME_VALUE"
    }
}