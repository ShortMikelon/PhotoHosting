package kz.aues.photohosting.domain.main.sign_up.entities

data class SignUpEntityWithoutAvatar(
    val name: String,
    val email: String,
    val password: String,
    val repeatPassword: String,
    val avatarUri: String
)