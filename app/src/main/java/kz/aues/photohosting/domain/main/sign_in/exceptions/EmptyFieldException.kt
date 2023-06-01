package kz.aues.photohosting.domain.main.sign_in.exceptions

import kz.aues.photohosting.domain.main.sign_in.entities.SignInField
import kz.samsungcampus.common.AppException

class EmptyFieldException(val field: SignInField) : AppException()

