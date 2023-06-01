package kz.aues.photohosting.connector

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kz.aues.photohosting.connector.mappers.PhotoMapper
import kz.aues.photohosting.data.ImageRemoteDataRepository
import kz.aues.photohosting.domain.ImageRemoteRepository
import kz.aues.photohosting.domain.main.tabs.images.models.ImageEntity
import kz.aues.photohosting.domain.main.tabs.images.models.ImageUploadEntity
import kz.samsungcampus.common.Resource
import javax.inject.Inject

class AdapterImageRemoteRepository @Inject constructor(
    private val imageRemoteRepository: ImageRemoteDataRepository,
    private val photoMapper: PhotoMapper
) : ImageRemoteRepository {

    override fun getImageById(id: String): Flow<Resource<ImageEntity>> =
        imageRemoteRepository.getImageById(id).map { resource ->
            resource.map { photoMapper.toImageEntity(it) }
        }

    override suspend fun putImage(photo: ImageUploadEntity): String {
       return imageRemoteRepository.putImage(photoMapper.toImageUploadDataEntity(photo))
    }

}