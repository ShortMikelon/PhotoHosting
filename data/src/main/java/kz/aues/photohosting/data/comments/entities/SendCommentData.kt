package kz.aues.photohosting.data.comments.entities

data class SendCommentData(
    val author: String,
    val authorId: String,
    val createdAt: Long,
    val text: String,
    val imageId: String
)