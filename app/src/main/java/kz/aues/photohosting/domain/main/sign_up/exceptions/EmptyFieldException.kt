package kz.aues.photohosting.domain.main.sign_up.exceptions

import kz.aues.photohosting.domain.main.sign_up.entities.SignUpField
import kz.samsungcampus.common.AppException

class EmptyFieldException(val field: SignUpField) : AppException()