package kz.aues.photohosting.data.account.entities

data class AccountDataEntity(
    val id: String,
    val name: String,
    val avatarUri: String,
    val imagesIds: List<String>,
    val followersIds: List<String>,
    val subscribesIds: List<String>
) {
    val countImages get() = imagesIds.size
    val countFollowers get() = followersIds.size
    val countSubscribes get() = subscribesIds.size
}

