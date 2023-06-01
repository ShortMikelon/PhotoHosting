package kz.aues.photohosting.data.image.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName
import kz.aues.photohosting.data.contracts.FirebaseImageContract

data class ImageFirebaseEntity(

    @get:PropertyName(FirebaseImageContract.ID)
    @set:PropertyName(FirebaseImageContract.ID)
    var id: String = "",

    @get:PropertyName(FirebaseImageContract.AUTHOR)
    @set:PropertyName(FirebaseImageContract.AUTHOR)
    var author: String = "",

    @get:PropertyName(FirebaseImageContract.AUTHOR_ID)
    @set:PropertyName(FirebaseImageContract.AUTHOR_ID)
    var authorId: String = "",

    @get:PropertyName(FirebaseImageContract.NAME)
    @set:PropertyName(FirebaseImageContract.NAME)
    var name: String = "",

    @get:PropertyName(FirebaseImageContract.DESCRIPTION)
    @set:PropertyName(FirebaseImageContract.DESCRIPTION)
    var description: String = "",

    @get:PropertyName(FirebaseImageContract.CREATED_AT)
    @set:PropertyName(FirebaseImageContract.CREATED_AT)
    var createdAt: Timestamp = Timestamp(0,0),

    @get:PropertyName(FirebaseImageContract.COUNT_COMMENTS)
    @set:PropertyName(FirebaseImageContract.COUNT_COMMENTS)
    var countComments: Int = 0,

    //    @get:PropertyName(FirebaseImageContract.TAGS)
    //    @set:PropertyName(FirebaseImageContract.TAGS)
    //    var tags: List<String> = emptyList(),

    @get:PropertyName(FirebaseImageContract.IMAGE_PATH)
    @set:PropertyName(FirebaseImageContract.IMAGE_PATH)
    var path: String = "",
)

fun ImageFirebaseEntity.toImageDataEntity() =
    ImageDataEntity(
        id = this.id,
        name = this.name,
        author = this.author,
        authorId = this.authorId,
        description = this.description,
        createdAt = this.createdAt.toDate().time,
        countComments = this.countComments,
        tags = emptyList(),
        path = this.path
    )