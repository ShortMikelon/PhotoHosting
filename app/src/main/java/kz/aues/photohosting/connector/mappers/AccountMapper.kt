package kz.aues.photohosting.connector.mappers

import kz.aues.photohosting.data.account.entities.AccountDataEntity
import kz.aues.photohosting.data.account.entities.AccountIdAndNameDataEntity
import kz.aues.photohosting.data.account.entities.SignInDataEntity
import kz.aues.photohosting.data.account.entities.SignUpDataEntity
import kz.aues.photohosting.domain.main.sign_in.entities.SignInEntity
import kz.aues.photohosting.domain.main.sign_up.entities.SignUpEntity
import kz.aues.photohosting.domain.main.tabs.profile.entities.AccountEntity
import kz.aues.photohosting.domain.main.tabs.profile.entities.AccountIdAndNameEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountMapper @Inject constructor() {

    fun toSignUpDataEntity(signUpEntity: SignUpEntity): SignUpDataEntity {
        val avatar = if (signUpEntity.avatar.contentEquals(SignUpEntity.DEFAULT_AVATAR)) {
            SignUpDataEntity.DEFAULT_AVATAR
        } else {
            signUpEntity.avatar
        }
        return SignUpDataEntity(
            name = signUpEntity.name,
            email = signUpEntity.email,
            password = signUpEntity.password,
            avatarUniqueName = signUpEntity.avatarUniqueName,
            avatar = avatar
        )
    }

    fun toSignInDataEntity(signInEntity: SignInEntity): SignInDataEntity =
        SignInDataEntity(
            email = signInEntity.email,
            password = signInEntity.password
        )

    fun toAccount(dataEntity: AccountDataEntity): AccountEntity {
        return AccountEntity(
            id = dataEntity.id,
            name = dataEntity.name,
            avatarUri = dataEntity.avatarUri,
            imagesIds = dataEntity.imagesIds,
            followersIds = dataEntity.followersIds,
            subscribesIds = dataEntity.subscribesIds
        )
    }

    fun toAccountIdAndNameEntity(accountIdAndName: AccountIdAndNameDataEntity): AccountIdAndNameEntity =
        AccountIdAndNameEntity(
            id = accountIdAndName.id,
            name = accountIdAndName.name
        )
}