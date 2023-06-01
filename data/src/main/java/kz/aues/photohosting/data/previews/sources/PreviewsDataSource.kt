package kz.aues.photohosting.data.previews.sources

import kz.aues.photohosting.data.previews.entities.PreviewDescriptionFirebaseData

interface PreviewsDataSource {

    suspend fun getPreviews(): List<PreviewDescriptionFirebaseData>

    suspend fun getPreviewsByAuthorId(authorId: String): List<PreviewDescriptionFirebaseData>

}

