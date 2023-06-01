package kz.aues.photohosting.data.account.entities


data class SignUpFirebaseStorageEntity(
    val avatarName: String,
    val avatar: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SignUpFirebaseStorageEntity

        if (avatarName != other.avatarName) return false
        if (!avatar.contentEquals(other.avatar)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = avatarName.hashCode()
        result = 31 * result + avatar.contentHashCode()
        return result
    }
}