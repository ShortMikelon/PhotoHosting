package kz.aues.photohosting.data.account.entities

data class SignUpFirestoreEntity(
    val name: String,
    val email: String,
    val password: String,
    val avatarUri: String,
)