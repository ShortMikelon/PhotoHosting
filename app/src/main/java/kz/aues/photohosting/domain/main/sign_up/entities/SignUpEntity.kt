package kz.aues.photohosting.domain.main.sign_up.entities

data class SignUpEntity(
    val name: String,
    val email: String,
    val password: String,
    val avatarUniqueName: String,
    val avatar: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SignUpEntity

        if (name != other.name) return false
        if (email != other.email) return false
        if (avatarUniqueName != other.avatarUniqueName) return false
        if (!avatar.contentEquals(other.avatar)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + avatarUniqueName.hashCode()
        result = 31 * result + avatar.contentHashCode()
        return result
    }

    companion object {
        val DEFAULT_AVATAR = byteArrayOf(-1)
        const val NO_AVATAR = "no avatar"
    }
}