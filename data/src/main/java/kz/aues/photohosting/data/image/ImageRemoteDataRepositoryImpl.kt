package kz.aues.photohosting.data.image

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kz.aues.photohosting.data.di.IODispatcherQualifier
import kz.aues.photohosting.data.image.models.ImageDataEntity
import kz.aues.photohosting.data.image.models.ImageUploadData
import kz.aues.photohosting.data.image.models.PhotoDescriptionUploadData
import kz.aues.photohosting.data.image.models.ImageUploadDataEntity
import kz.aues.photohosting.data.image.models.toImageDataEntity
import kz.aues.photohosting.data.ImageRemoteDataRepository
import kz.aues.photohosting.data.exceptions.NetworkDataException
import kz.aues.photohosting.data.image.exceptions.FailedCreateImageDataException
import kz.aues.photohosting.data.image.sources.ImageDataSource
import kz.aues.photohosting.data.image.sources.ImageDescriptionDataSource
import kz.aues.photohosting.data.utils.createResource
import kz.samsungcampus.common.flow.LazyFlowSubject
import kz.samsungcampus.common.flow.LazyFlowSubjectFactory
import kz.samsungcampus.common.Resource
import kz.samsungcampus.common.flow.lazyFlowSubject
import javax.inject.Inject

class ImageRemoteDataRepositoryImpl @Inject constructor(
    private val imageDescriptionSource: ImageDescriptionDataSource,
    private val imageSource: ImageDataSource,
    @IODispatcherQualifier private val dispatcher: CoroutineDispatcher,
    private val lazyFlowSubjectFactory: LazyFlowSubjectFactory
) : ImageRemoteDataRepository {

    private lateinit var imageSubject: LazyFlowSubject<ImageDataEntity>

    override fun getImageById(id: String): Flow<Resource<ImageDataEntity>> {
        imageSubject = lazyFlowSubjectFactory.create {
            imageDescriptionSource.getImageDescriptionById(id).toImageDataEntity()
        }
        return imageSubject.listen()
    }

    override suspend fun putImage(photo: ImageUploadDataEntity): String {
        try {
            val path = imageSource.upload(ImageUploadData(photo.name, photo.imageBytes))
            return imageDescriptionSource.uploadData(
                PhotoDescriptionUploadData(
                    name = photo.name,
                    authorId = photo.authorId,
                    author = photo.author,
                    description = photo.description,
                    createdAt = photo.createdAt,
                    tags = photo.tags,
                    path = path,
                )
            )
        } catch (ex: FirebaseNetworkException) {
            throw NetworkDataException()
        } catch (ex: FirebaseFirestoreException) {
            throw FailedCreateImageDataException()
        }
    }
}

