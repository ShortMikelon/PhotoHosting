package kz.aues.photohosting.domain.main.tabs.profile

import kotlinx.coroutines.flow.Flow
import kz.aues.photohosting.domain.AccountRepository
import kz.aues.photohosting.domain.PreviewsRepository
import kz.aues.photohosting.domain.main.tabs.previews.entities.PreviewEntity
import kz.samsungcampus.common.Resource
import javax.inject.Inject

class GetUserPreviewsUseCase @Inject constructor(
    private val previewsRepository: PreviewsRepository,
    private val accountRepository: AccountRepository
) {

    suspend fun getPreviews(id: String?): Flow<Resource<List<PreviewEntity>>> {
        val authorId = id ?: accountRepository.getCurrentAccountNameAndId().id
        return previewsRepository.getPreviewsByAuthorId(authorId)
    }

    fun reload() {
        previewsRepository.reloadPreviewsByAuthorId()
    }
}