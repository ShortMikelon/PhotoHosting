package kz.aues.photohosting.data.account

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.flow.Flow
import kz.aues.photohosting.data.AccountDataRepository
import kz.aues.photohosting.data.account.entities.*
import kz.aues.photohosting.data.account.exceptions.*
import kz.aues.photohosting.data.account.sources.AccountAvatarDataSource
import kz.aues.photohosting.data.account.sources.AccountDataSource
import kz.aues.photohosting.data.exceptions.NetworkDataException
import kz.aues.photohosting.data.settings.PreferencesDataStoreSettingsDataSource
import kz.aues.photohosting.data.settings.SettingsDataSource
import kz.samsungcampus.common.Resource
import kz.samsungcampus.common.flow.LazyFlowSubject
import kz.samsungcampus.common.flow.LazyFlowSubjectFactory
import javax.inject.Inject

class DefaultAccountDataRepository @Inject constructor(
    private val accountDataSource: AccountDataSource,
    private val accountAvatarDataSource: AccountAvatarDataSource,
    private val settingsDataSource: SettingsDataSource,
    private val lazyFlowSubjectFactory: LazyFlowSubjectFactory,
) : AccountDataRepository {

    private var account = lazyFlowSubjectFactory.create {
        accountDataSource.read(settingsDataSource.getUid()).toAccountData()
    }

    override suspend fun isSignIn(): Boolean {
        val uid = settingsDataSource.getUid()
        return uid != PreferencesDataStoreSettingsDataSource.DEFAULT_UID_VALUE
    }

    override suspend fun signIn(signInData: SignInDataEntity) {
        try {
            val uid = accountDataSource.signIn(signInData)
            val account = accountDataSource.read(uid)
            settingsDataSource.setUid(uid)
            settingsDataSource.setAccountName(account.name)

        } catch (ex: FirebaseAuthInvalidCredentialsException) {
            throw AuthInvalidPasswordDataException()
        } catch (ex: FirebaseAuthInvalidUserException) {
            throw AuthInvalidEmailDataException()
        }
    }

    override suspend fun signOut() {
        accountDataSource.signOut()
        settingsDataSource.setUid(PreferencesDataStoreSettingsDataSource.DEFAULT_UID_VALUE)
        settingsDataSource.setAccountName(PreferencesDataStoreSettingsDataSource.DEFAULT_USERNAME_VALUE)
    }

    override suspend fun signUp(signUpDataEntity: SignUpDataEntity) {
        try {
            val avatarUri = accountAvatarDataSource.upload(
                SignUpFirebaseStorageEntity(
                    signUpDataEntity.avatarUniqueName,
                    signUpDataEntity.avatar
                )
            )
            accountDataSource.create(
                SignUpFirestoreEntity(
                    name = signUpDataEntity.name,
                    email = signUpDataEntity.email,
                    password = signUpDataEntity.password,
                    avatarUri = avatarUri
                )
            )
        } catch (e: FirebaseNetworkException) {
            throw NetworkDataException()
        } catch (e: FirebaseAuthUserCollisionException) {
            throw AccountAlreadyExistsDataException()
        } catch (e: FirebaseAuthWeakPasswordException) {
            throw WeakPasswordDataException()
        }
    }

    override suspend fun getCurrentAccountIdAndName(): AccountIdAndNameDataEntity {
        val id = settingsDataSource.getUid()
        val name = settingsDataSource.getAccountName()
        if (id == PreferencesDataStoreSettingsDataSource.DEFAULT_UID_VALUE) throw NotAuthorizedDataException()
        if (name == PreferencesDataStoreSettingsDataSource.DEFAULT_USERNAME_VALUE) throw NotAuthorizedDataException()
        return AccountIdAndNameDataEntity(id, name)
    }

    override fun getAccountById(accountId: String): Flow<Resource<AccountDataEntity>> {
        account.newAsyncLoad { accountDataSource.read(accountId).toAccountData() }
        return account.listen()
    }

    override suspend fun addImage(imageId: String) {
        accountDataSource.addImage(imageId)
    }

    override fun update() {
        TODO("Not yet implemented")
    }

    override fun delete() {
        TODO("Not yet implemented")
    }

    override fun reload() {
        account!!.newAsyncLoad()
    }

}