package kz.aues.photohosting.domain.main.tabs.images.models

data class ImageUploadEntity(
    val name: String,
    val author: String,
    val authorId: String,
    val description: String,
    val createdAt: Long,
    val tags: List<String>,
    val imageBytes: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImageUploadEntity

        if (name != other.name) return false
        if (author != other.author) return false
        if (authorId != other.authorId) return false
        if (description != other.description) return false
        if (createdAt != other.createdAt) return false
        if (tags != other.tags) return false
        if (!imageBytes.contentEquals(other.imageBytes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + author.hashCode()
        result = 31 * result + authorId.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + tags.hashCode()
        result = 31 * result + imageBytes.contentHashCode()
        return result
    }
}