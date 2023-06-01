package kz.aues.photohosting.connector.mappers

import kz.aues.photohosting.data.image.models.ImageDataEntity
import kz.aues.photohosting.data.image.models.ImageUploadDataEntity
import kz.aues.photohosting.domain.main.tabs.images.models.ImageEntity
import kz.aues.photohosting.domain.main.tabs.images.models.ImageUploadEntity
import javax.inject.Inject

class PhotoMapper @Inject constructor() {

    fun toImageEntity(imageDataEntity: ImageDataEntity) =
        ImageEntity(
            id = imageDataEntity.id,
            name = imageDataEntity.name,
            author = imageDataEntity.author,
            authorId = imageDataEntity.authorId,
            description = imageDataEntity.description,
            createdAt = imageDataEntity.createdAt,
            countComments = imageDataEntity.countComments,
            tags = imageDataEntity.tags,
            path = imageDataEntity.path
        )

    fun toImageUploadDataEntity(imageUploadEntity: ImageUploadEntity): ImageUploadDataEntity =
        ImageUploadDataEntity(
            name = imageUploadEntity.name,
            author = imageUploadEntity.author,
            authorId = imageUploadEntity.authorId,
            description = imageUploadEntity.description,
            createdAt = imageUploadEntity.createdAt,
            tags = imageUploadEntity.tags,
            imageBytes = imageUploadEntity.imageBytes
        )
}

