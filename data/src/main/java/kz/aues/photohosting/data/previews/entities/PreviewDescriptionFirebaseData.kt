package kz.aues.photohosting.data.previews.entities

import android.net.Uri
import com.google.firebase.firestore.PropertyName
import kz.aues.photohosting.data.contracts.FirebaseImageContract

data class PreviewDescriptionFirebaseData(
    var id: String = "",
    @get:PropertyName(FirebaseImageContract.IMAGE_PATH)
    @set:PropertyName(FirebaseImageContract.IMAGE_PATH)
    var previewPath: String = ""
)

fun PreviewDescriptionFirebaseData.toPreviewDataEntity() =
    PreviewDataEntity(
        id = id,
        previewPath = previewPath
    )

fun PreviewDescriptionFirebaseData.toPhotoPreviewData() =
    PhotoPreviewData(
        id = id,
        uri = Uri.parse(previewPath)
    )

fun List<PreviewDescriptionFirebaseData>.toPreviewDataEntity() =
    map { it.toPreviewDataEntity() }

