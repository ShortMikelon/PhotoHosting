package kz.aues.photohosting.data.image.remote_repositories.firebase.sources

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import kz.aues.photohosting.data.image.models.ImageFirebaseEntity
import kz.aues.photohosting.data.image.sources.FirestoreImageDescriptionDataSource
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FirebasePhotoDataRemoteDataSourceTest {


    @Test
    fun getPhotoDataByIdCallsToObjectAndReturnPhotoData() = runTest {
        val firestore = mockk<FirebaseFirestore>()
        val collection = mockk<CollectionReference>()
        val document = mockk<DocumentReference>()
        val snapshot = mockk<DocumentSnapshot>()
        every { firestore.collection(any()) } returns collection
        every { collection.document(any()) } returns document
        coEvery { document.get().await() } returns snapshot
        every { snapshot.toObject(ImageFirebaseEntity::class.java) } returns
                createTestPhotoDataFirebaseData()
        val source = createSource(firestore)

        val photoData = source.getImageDescriptionById("id")

        verify(exactly = 1) { snapshot.toObject(ImageFirebaseEntity::class.java) }
        confirmVerified(snapshot)
        assertEquals(photoData, createTestPhotoDataFirebaseData())
    }

    @Test
    fun test() {
        assertEquals(2,23)
    }

    private fun createFirestore(): FirebaseFirestore = mockk()

    private fun createSource(
        firestore: FirebaseFirestore = createFirestore()
    ): FirestoreImageDescriptionDataSource =
        FirestoreImageDescriptionDataSource(firestore)

    private fun createTestPhotoDataFirebaseData() =
        ImageFirebaseEntity(
            id = "1",
            authorId = "21",
            name = "name",
            description = "description",
            createdAt = 1L,
            tags = listOf("anime"),
            path = "path"
        )
}