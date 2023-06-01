package kz.aues.photohosting.data.image.sources

import kz.aues.photohosting.data.image.models.PhotoDescriptionUploadData
import kz.aues.photohosting.data.image.models.ImageFirebaseEntity

interface ImageDescriptionDataSource {

    suspend fun getImageDescriptionById(id: String): ImageFirebaseEntity

    suspend fun uploadData(photoDescription: PhotoDescriptionUploadData): String
}