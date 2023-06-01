package kz.aues.photohosting.data.comments.entities

import com.google.firebase.firestore.PropertyName
import kz.aues.photohosting.data.contracts.FirebaseCommentsContract
import java.util.*

data class CommentFirebaseEntity(
    @get:PropertyName(FirebaseCommentsContract.ID)
    @set:PropertyName(FirebaseCommentsContract.ID)
    var id: String = "",

    @get:PropertyName(FirebaseCommentsContract.AUTHOR)
    @set:PropertyName(FirebaseCommentsContract.AUTHOR)
    var author: String = "",

    @get:PropertyName(FirebaseCommentsContract.AUTHOR_ID)
    @set:PropertyName(FirebaseCommentsContract.AUTHOR_ID)
    var authorId: String = "",

    @get:PropertyName(FirebaseCommentsContract.CREATED_AT)
    @set:PropertyName(FirebaseCommentsContract.CREATED_AT)
    var createdAt: Long = 0L,

    @get:PropertyName(FirebaseCommentsContract.TEXT)
    @set:PropertyName(FirebaseCommentsContract.TEXT)
    var text: String = ""
) {
    companion object {
        fun fromCommentFirebaseEntity(comment: CommentDataEntity) =
            CommentFirebaseEntity(
                id = comment.id,
                author = comment.author,
                authorId = comment.authorId,
                createdAt = comment.createdAt,
                text = comment.text
            )
    }
}

fun CommentFirebaseEntity.toCommentDataEntity() =
    CommentDataEntity(
        id = this.id,
        author = this.author,
        authorId = this.authorId,
        createdAt = this.createdAt,
        text = this.text
    )
