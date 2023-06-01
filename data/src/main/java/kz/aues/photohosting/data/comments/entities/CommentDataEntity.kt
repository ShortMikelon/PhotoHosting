package kz.aues.photohosting.data.comments.entities

data class CommentDataEntity(
    val id: String,
    val author: String,
    val authorId: String,
    val createdAt: Long,
    val text: String
)