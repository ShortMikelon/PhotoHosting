package kz.aues.photohosting.connector

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kz.aues.photohosting.connector.mappers.CommentMapper
import kz.aues.photohosting.data.CommentsDataRepository
import kz.aues.photohosting.domain.CommentsRepository
import kz.aues.photohosting.domain.main.add_comment.entities.SendCommentEntity
import kz.aues.photohosting.domain.main.comments.entities.CommentEntity
import kz.samsungcampus.common.Resource
import javax.inject.Inject

class AdapterCommentsRepository @Inject constructor(
    private val dataRepository: CommentsDataRepository,
    private val mapper: CommentMapper
) : CommentsRepository {

    override fun getComments(imageId: String): Flow<Resource<List<CommentEntity>>> {
        return dataRepository.getComments(imageId).map { resource ->
            resource.map { comments ->
                comments.map { commentData -> mapper.toComment(commentData) }
            }
        }
    }

    override suspend fun putComment(comment: SendCommentEntity): Boolean {
        return dataRepository.putComment(mapper.toSendCommentData(comment))
    }

    override suspend fun putCommentWithoutUpdate(comment: SendCommentEntity): Boolean {
        return dataRepository.putCommentWithoutUpdate(mapper.toSendCommentData(comment))
    }

    override fun reload() {
        dataRepository.reload()
    }

}