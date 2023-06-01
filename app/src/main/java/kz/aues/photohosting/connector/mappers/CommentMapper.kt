package kz.aues.photohosting.connector.mappers

import kz.aues.photohosting.data.comments.entities.CommentDataEntity
import kz.aues.photohosting.data.comments.entities.SendCommentData
import kz.aues.photohosting.domain.main.add_comment.entities.SendCommentEntity
import kz.aues.photohosting.domain.main.comments.entities.CommentEntity
import javax.inject.Inject

class CommentMapper @Inject constructor() {

    fun toComment(commentData: CommentDataEntity): CommentEntity =
        CommentEntity(
            id = commentData.id,
            author = commentData.author,
            authorId = commentData.authorId,
            createdAt = commentData.createdAt,
            text = commentData.text
        )

    fun toCommentData(commentEntity: CommentEntity): CommentDataEntity =
        CommentDataEntity(
            id = commentEntity.id,
            author = commentEntity.author,
            authorId = commentEntity.authorId,
            createdAt = commentEntity.createdAt,
            text = commentEntity.text
        )

    fun toSendCommentData(sendCommentEntity: SendCommentEntity): SendCommentData =
        SendCommentData(
            imageId = sendCommentEntity.imageId,
            author = sendCommentEntity.author,
            authorId = sendCommentEntity.authorId,
            createdAt = sendCommentEntity.createdAt,
            text = sendCommentEntity.text,
        )
}