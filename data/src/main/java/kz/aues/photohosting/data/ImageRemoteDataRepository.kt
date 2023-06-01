package kz.aues.photohosting.data

import kotlinx.coroutines.flow.Flow
import kz.aues.photohosting.data.image.models.ImageDataEntity
import kz.aues.photohosting.data.image.models.ImageUploadDataEntity
import kz.samsungcampus.common.Resource

interface ImageRemoteDataRepository {

    fun getImageById(id: String): Flow<Resource<ImageDataEntity>>

    suspend fun putImage(photo: ImageUploadDataEntity): String

}