package kz.aues.photohosting.data.settings

import androidx.datastore.preferences.core.stringPreferencesKey

object AccountSettingsScheme {

    val KEY_ACCOUNT_UID = stringPreferencesKey("account_uid")
    val KEY_ACCOUNT_USERNAME = stringPreferencesKey("account_username")

}