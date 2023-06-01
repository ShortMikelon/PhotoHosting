package kz.aues.photohosting.data.previews

import kotlinx.coroutines.flow.Flow
import kz.aues.photohosting.data.PreviewsDataRepository
import kz.aues.photohosting.data.previews.entities.PreviewDataEntity
import kz.aues.photohosting.data.previews.entities.toPreviewDataEntity
import kz.aues.photohosting.data.previews.sources.PreviewsDataSource
import kz.samsungcampus.common.flow.LazyFlowSubject
import kz.samsungcampus.common.flow.LazyFlowSubjectFactory
import kz.samsungcampus.common.Resource
import javax.inject.Inject

class DefaultPreviewsDataRepository @Inject constructor(
    private val previewDataSource: PreviewsDataSource,
    private val lazyFlowSubjectFactory: LazyFlowSubjectFactory
) : PreviewsDataRepository {

    private val previewsSubject = lazyFlowSubjectFactory.create {
        previewDataSource.getPreviews().toPreviewDataEntity()
    }

    private lateinit var previewsByAuthorSubject: LazyFlowSubject<List<PreviewDataEntity>>

    override fun getPreviews(): Flow<Resource<List<PreviewDataEntity>>> {
        return previewsSubject.listen()
    }

    override fun getPreviewsByAuthorId(authorId: String): Flow<Resource<List<PreviewDataEntity>>> {
        previewsByAuthorSubject = lazyFlowSubjectFactory.create {
            previewDataSource.getPreviewsByAuthorId(authorId).toPreviewDataEntity()
        }
        return previewsByAuthorSubject.listen()
    }

    override fun reloadPreviews() {
        previewsSubject.newAsyncLoad()
    }

    override fun reloadPreviewsByAuthorId() {
        previewsByAuthorSubject.newAsyncLoad()
    }
}