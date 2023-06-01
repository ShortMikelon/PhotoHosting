package kz.aues.photohosting.data.account.sources

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import kz.aues.photohosting.data.account.entities.SignUpDataEntity
import kz.aues.photohosting.data.account.entities.SignUpFirebaseStorageEntity
import javax.inject.Inject

class FirebaseAccountAvatarDataSource @Inject constructor(
    private val storage: FirebaseStorage
) : AccountAvatarDataSource {
    override suspend fun upload(signUpFirebaseStorageEntity: SignUpFirebaseStorageEntity): String {
        val newImage: StorageReference
        if (signUpFirebaseStorageEntity.avatar.contentEquals(SignUpDataEntity.DEFAULT_AVATAR)) {
            newImage = storage.reference.child(DEFAULT_AVATAR)
        } else {
            newImage =
                storage.reference.child(BASE_URL + signUpFirebaseStorageEntity.avatarName)
            newImage.putBytes(signUpFirebaseStorageEntity.avatar).await()
        }

        val uri = newImage.downloadUrl.await()

        return uri.toString()
    }

    companion object {
        private const val BASE_URL = "avatars/"
        private const val DEFAULT_AVATAR = BASE_URL + "default_avatar.jpeg"
    }
}