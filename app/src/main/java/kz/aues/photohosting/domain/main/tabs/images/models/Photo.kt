package kz.aues.photohosting.domain.main.tabs.images.models

import android.net.Uri

data class Photo(
    val id: String,
    val name: String,
    val author: String,
    val authorId: String,
    val description: String,
    val tags: List<String>,
    var countComments: Int,
    val createdAt: Long,
    var uri: Uri? = null
)
