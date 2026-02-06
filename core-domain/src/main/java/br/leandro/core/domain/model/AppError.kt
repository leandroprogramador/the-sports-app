package br.leandro.core.domain.model

import java.io.Serializable

sealed class AppError(
) : Throwable(), Serializable {
    object NoConnection : AppError()
    object ServiceUnavailable : AppError()
    object Unknown : AppError()
    
}