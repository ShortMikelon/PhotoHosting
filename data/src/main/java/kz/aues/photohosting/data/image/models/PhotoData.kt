package kz.aues.photohosting.data.image.models

import android.net.Uri

data class PhotoData (
    val id: String ,
    val name: String ,
    val author: String,
    val authorId: String ,
    val description: String ,
    val tags: List<String>,
    val countComments: Int,
    val createdAt: Long,
    val uri: Uri?
)

