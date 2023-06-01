package kz.aues.photohosting.domain.main.add_comment.entities

data class SendCommentEntity(
    val author: String,
    val authorId: String,
    val createdAt: Long,
    val text: String,
    val imageId: String
)
