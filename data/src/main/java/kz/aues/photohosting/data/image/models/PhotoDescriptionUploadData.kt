package kz.aues.photohosting.data.image.models

data class PhotoDescriptionUploadData(
    val authorId: String ,
    val author: String,
    val name: String ,
    val description: String ,
    val createdAt: Long,
    val tags: List<String>,
    val path: String
)