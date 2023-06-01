package kz.aues.photohosting.data

import kotlinx.coroutines.flow.Flow
import kz.aues.photohosting.data.comments.entities.CommentDataEntity
import kz.aues.photohosting.data.comments.entities.SendCommentData
import kz.samsungcampus.common.Resource

interface CommentsDataRepository {

    fun getComments(imageId: String): Flow<Resource<List<CommentDataEntity>>>

    suspend fun putComment(comment: SendCommentData): Boolean

    suspend fun putCommentWithoutUpdate(comment: SendCommentData): Boolean

    fun reload()

}