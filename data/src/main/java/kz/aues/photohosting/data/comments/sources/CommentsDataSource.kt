package kz.aues.photohosting.data.comments.sources

import kz.aues.photohosting.data.comments.entities.CommentFirebaseEntity
import kz.aues.photohosting.data.comments.entities.SendCommentData

interface CommentsDataSource {

    suspend fun getComments(imageId: String): List<CommentFirebaseEntity>

    suspend fun putComment(comment: SendCommentData)

}

