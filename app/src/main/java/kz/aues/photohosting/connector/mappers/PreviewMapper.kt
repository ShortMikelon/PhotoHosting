package kz.aues.photohosting.connector.mappers

import kz.aues.photohosting.data.previews.entities.PreviewDataEntity
import kz.aues.photohosting.domain.main.tabs.previews.entities.PreviewEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreviewMapper @Inject constructor() {

    fun toPreviewEntity(previewDataEntity: PreviewDataEntity): PreviewEntity =
        PreviewEntity(
            previewDataEntity.id,
            previewDataEntity.previewPath
        )
}