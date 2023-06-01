package kz.aues.photohosting.domain

import kotlinx.coroutines.flow.Flow
import kz.aues.photohosting.domain.main.tabs.previews.entities.PreviewEntity
import kz.samsungcampus.common.Resource

interface PreviewsRepository {

    fun getPreviews(): Flow<Resource<List<PreviewEntity>>>

    fun getPreviewsByAuthorId(authorId: String): Flow<Resource<List<PreviewEntity>>>

    fun reloadPreviews()

    fun reloadPreviewsByAuthorId()

}