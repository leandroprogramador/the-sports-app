package br.leandro.core.data.mapper

import br.leandro.core.domain.model.AppError
import br.leandro.core.network.error.NetworkError


fun NetworkError.toAppError(): AppError =
    when (this) {
        NetworkError.NoConnection -> AppError.NoConnection
        NetworkError.ServiceUnavailable -> AppError.ServiceUnavailable
        NetworkError.Unknown -> AppError.Unknown
    }
