package kz.aues.photohosting.domain

import kotlinx.coroutines.flow.Flow
import kz.aues.photohosting.domain.main.add_comment.entities.SendCommentEntity
import kz.aues.photohosting.domain.main.comments.entities.CommentEntity
import kz.samsungcampus.common.Resource

interface CommentsRepository {

    fun getComments(imageId: String): Flow<Resource<List<CommentEntity>>>

    suspend fun putComment(comment: SendCommentEntity): Boolean

    suspend fun putCommentWithoutUpdate(comment: SendCommentEntity): Boolean

    fun reload()

}