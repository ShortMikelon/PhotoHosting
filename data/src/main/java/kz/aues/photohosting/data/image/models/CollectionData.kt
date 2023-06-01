package kz.aues.photohosting.data.image.models

data class CollectionData(
    val id: String,
    val name: String,
    val imagesId: List<String>
)