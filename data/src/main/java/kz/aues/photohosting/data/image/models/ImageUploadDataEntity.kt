package kz.aues.photohosting.data.image.models

data class ImageUploadDataEntity(
    val name: String,
    val author: String,
    val authorId: String,
    val description: String,
    val createdAt: Long,
    val tags: List<String>,
    val imageBytes: ByteArray
)