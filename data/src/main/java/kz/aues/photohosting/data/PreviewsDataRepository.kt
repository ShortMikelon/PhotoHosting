package kz.aues.photohosting.data

import kotlinx.coroutines.flow.Flow
import kz.aues.photohosting.data.previews.entities.PreviewDataEntity
import kz.samsungcampus.common.Resource

interface PreviewsDataRepository {

    fun getPreviews(): Flow<Resource<List<PreviewDataEntity>>>

    fun getPreviewsByAuthorId(authorId: String): Flow<Resource<List<PreviewDataEntity>>>

    fun reloadPreviews()

    fun reloadPreviewsByAuthorId()

}