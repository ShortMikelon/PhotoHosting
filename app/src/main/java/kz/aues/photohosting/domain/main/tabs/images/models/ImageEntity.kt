package kz.aues.photohosting.domain.main.tabs.images.models

data class ImageEntity(
    val id: String,
    val author: String,
    val authorId: String,
    val name: String,
    val description: String,
    val countComments: Int,
    val createdAt: Long,
    val tags: List<String>,
    val path: String,
)

