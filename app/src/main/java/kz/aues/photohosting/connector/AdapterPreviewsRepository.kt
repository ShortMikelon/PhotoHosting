package kz.aues.photohosting.connector

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kz.aues.photohosting.connector.mappers.PreviewMapper
import kz.aues.photohosting.data.PreviewsDataRepository
import kz.aues.photohosting.domain.PreviewsRepository
import kz.aues.photohosting.domain.main.tabs.previews.entities.PreviewEntity
import kz.samsungcampus.common.Resource
import javax.inject.Inject

class AdapterPreviewsRepository @Inject constructor(
    private val dataRepository: PreviewsDataRepository,
    private val mapper: PreviewMapper
) : PreviewsRepository {

    override fun getPreviews(): Flow<Resource<List<PreviewEntity>>> {
        return dataRepository.getPreviews().map { resource ->
            resource.map { previews ->
                previews.map { preview ->
                    mapper.toPreviewEntity(preview)
                }
            }
        }
    }

    override fun getPreviewsByAuthorId(authorId: String): Flow<Resource<List<PreviewEntity>>> {
        return dataRepository.getPreviewsByAuthorId(authorId).map { resource ->
            resource.map { previews ->
                previews.map { preview ->
                    mapper.toPreviewEntity(preview)
                }
            }
        }
    }

    override fun reloadPreviews() {
        return dataRepository.reloadPreviews()
    }

    override fun reloadPreviewsByAuthorId() {
        return dataRepository.reloadPreviewsByAuthorId()
    }

}

