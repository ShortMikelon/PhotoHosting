package kz.aues.photohosting.domain

import kotlinx.coroutines.flow.Flow
import kz.aues.photohosting.domain.main.tabs.images.models.ImageEntity
import kz.aues.photohosting.domain.main.tabs.images.models.ImageUploadEntity
import kz.samsungcampus.common.Resource

interface ImageRemoteRepository {

    fun getImageById(id: String): Flow<Resource<ImageEntity>>

    suspend fun putImage(photo: ImageUploadEntity): String

}