package kz.aues.photohosting.domain.main.tabs.profile.entities

data class AccountEntity(
    val id: String,
    val name: String,
    val avatarUri: String,
    val imagesIds: List<String>,
    val followersIds: List<String>,
    val subscribesIds: List<String>
)

