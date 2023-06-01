package kz.aues.photohosting.data.account.sources

import kz.aues.photohosting.data.account.entities.SignUpFirebaseStorageEntity

interface AccountAvatarDataSource {
    suspend fun upload(signUpFirebaseStorageEntity: SignUpFirebaseStorageEntity): String
}

