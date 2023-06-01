package kz.aues.photohosting.domain.main.comments.entities

data class CommentEntity(
    val id: String,
    val author: String,
    val authorId: String,
    val createdAt: Long,
    val text: String
)