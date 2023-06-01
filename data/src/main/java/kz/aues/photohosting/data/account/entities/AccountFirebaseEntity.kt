package kz.aues.photohosting.data.account.entities

import com.google.firebase.firestore.PropertyName
import kz.aues.photohosting.data.contracts.FirebaseAccountContract

data class AccountFirebaseEntity(
    @get:PropertyName(FirebaseAccountContract.ID)
    var id: String = "",
    @get:PropertyName(FirebaseAccountContract.NAME)
    var name: String = "",
    @get:PropertyName(FirebaseAccountContract.AVATAR_URI)
    var avatarUri: String = "",
    @get:PropertyName(FirebaseAccountContract.IMAGES_IDS)
    var imagesIds: List<String> = emptyList(),
    @get:PropertyName(FirebaseAccountContract.FOLLOWERS_IDS)
    var followersIds: List<String> = emptyList(),
    @get:PropertyName(FirebaseAccountContract.SUBSCRIBES_IDS)
    var subscribesIds: List<String> = emptyList()
)

fun AccountFirebaseEntity.toAccountData(): AccountDataEntity =
    AccountDataEntity(
        id = id,
        name = name,
        avatarUri = avatarUri,
        imagesIds = imagesIds,
        followersIds = followersIds,
        subscribesIds = subscribesIds,
    )