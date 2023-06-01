package kz.aues.photohosting.domain.main.add_comment

import kz.aues.photohosting.domain.AccountRepository
import kz.aues.photohosting.domain.CommentsRepository
import kz.aues.photohosting.domain.main.add_comment.entities.SendCommentEntity
import java.util.*
import javax.inject.Inject

class SendCommentUseCase @Inject constructor(
    private val commentsRepository: CommentsRepository,
    private val accountRepository: AccountRepository
) {

    suspend fun send(text: String, imageId: String, shouldCommentsUpdated: Boolean): Boolean {
        val comment = createComment(imageId, text)
        return if (shouldCommentsUpdated) commentsRepository.putComment(comment)
        else commentsRepository.putCommentWithoutUpdate(comment)
    }

    private suspend fun createComment(imageId: String, text: String): SendCommentEntity {
        val accountData = accountRepository.getCurrentAccountNameAndId()
        return SendCommentEntity(
            imageId = imageId,
            author = accountData.name,
            authorId = accountData.id,
            createdAt = Calendar.getInstance().timeInMillis,
            text = text,
        )
    }
}