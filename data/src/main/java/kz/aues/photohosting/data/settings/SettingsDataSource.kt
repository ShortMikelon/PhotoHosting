package kz.aues.photohosting.data.settings

interface SettingsDataSource {

    suspend fun getUid(): String

    suspend fun setUid(uid: String?)

    suspend fun getAccountName(): String

    suspend fun setAccountName(username: String?)

}