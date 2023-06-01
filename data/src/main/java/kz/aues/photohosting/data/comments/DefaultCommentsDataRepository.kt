package kz.aues.photohosting.data.comments

import kotlinx.coroutines.flow.Flow
import kz.aues.photohosting.data.CommentsDataRepository
import kz.aues.photohosting.data.comments.entities.CommentDataEntity
import kz.aues.photohosting.data.comments.entities.SendCommentData
import kz.aues.photohosting.data.comments.entities.toCommentDataEntity
import kz.aues.photohosting.data.comments.sources.CommentsDataSource
import kz.samsungcampus.common.flow.LazyFlowSubject
import kz.samsungcampus.common.flow.LazyFlowSubjectFactory
import kz.samsungcampus.common.Resource
import javax.inject.Inject

class DefaultCommentsDataRepository @Inject constructor(
    private val commentsSource: CommentsDataSource,
    private val lazyFlowSubjectFactory: LazyFlowSubjectFactory
) : CommentsDataRepository {

    private lateinit var commentsSubject: LazyFlowSubject<List<CommentDataEntity>>

    override fun getComments(imageId: String): Flow<Resource<List<CommentDataEntity>>> {
        commentsSubject = lazyFlowSubjectFactory.create {
            commentsSource.getComments(imageId).map { it.toCommentDataEntity() }
        }
        return commentsSubject.listen()
    }

    override suspend fun putComment(comment: SendCommentData): Boolean {
        return try {
            commentsSource.putComment(comment)
            commentsSubject.newAsyncLoad(silently = true)
            true
        } catch (ignored: Exception) {
            false
        }
    }

    override suspend fun putCommentWithoutUpdate(comment: SendCommentData): Boolean {
        return try {
            commentsSource.putComment(comment)
            true
        } catch (ignored: Exception) {
            false
        }
    }

    override fun reload() {
        commentsSubject.newAsyncLoad()
    }
    
}