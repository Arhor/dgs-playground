package com.github.mburyshynets.dgs.service

import com.netflix.graphql.dgs.exceptions.DgsException
import com.netflix.graphql.types.errors.ErrorType

class DgsPermissionDeniedException(message: String = "Permission denied!") : DgsException(
    message = message,
    errorType = ErrorType.PERMISSION_DENIED,
)
