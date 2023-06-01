package kz.aues.photohosting.domain.main.tabs.create_image

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kz.aues.photohosting.data.exceptions.NetworkDataException
import kz.aues.photohosting.data.image.exceptions.FailedCreateImageDataException
import kz.aues.photohosting.domain.AccountRepository
import kz.aues.photohosting.domain.ImageRemoteRepository
import kz.aues.photohosting.domain.exceptions.NetworkException
import kz.aues.photohosting.domain.main.tabs.create_image.entities.CreateImageEntity
import kz.aues.photohosting.domain.main.tabs.create_image.exceptions.EmptyNameFieldException
import kz.aues.photohosting.domain.main.tabs.create_image.exceptions.FailedCreateImageException
import kz.aues.photohosting.domain.main.tabs.create_image.exceptions.InvalidUrlException
import kz.aues.photohosting.domain.main.tabs.images.models.ImageUploadEntity
import kz.aues.photohosting.utlis.getBytesFromUri
import javax.inject.Inject

class CreateImageUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val imageRepository: ImageRemoteRepository,
    private val accountRepository: AccountRepository
) {

    suspend fun createImage(createImageEntity: CreateImageEntity) {
        createImageEntity.isValid()
        val bytes = getBytesFromUri(context, createImageEntity.localUrl)
        val account = accountRepository.getCurrentAccountNameAndId()
        val imageUploadEntity = ImageUploadEntity(
            name = createImageEntity.name,
            description = createImageEntity.description,
            author = account.name,
            authorId = account.id,
            createdAt = System.currentTimeMillis(),
            tags = emptyList(),
            imageBytes = bytes
        )
        try {
            val imageId = imageRepository.putImage(imageUploadEntity)
            accountRepository.addImage(imageId)
        } catch (ex: NetworkDataException) {
            throw NetworkException()
        } catch (ex: FailedCreateImageDataException) {
            throw FailedCreateImageException()
        }
    }

    private fun CreateImageEntity.isValid() {
        if (name.isEmpty()) throw EmptyNameFieldException()
        if (localUrl.toString().isEmpty()) throw InvalidUrlException()
    }
}