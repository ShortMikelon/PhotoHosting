package kz.aues.photohosting.data.image.models

data class ImageDataEntity(
    val id: String,
    val name: String,
    val author: String,
    val authorId: String,
    val description: String,
    val tags: List<String>,
    val countComments: Int,
    val createdAt: Long,
    val path: String,
)