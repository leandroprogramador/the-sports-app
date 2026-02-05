package br.leandro.core.domain.model

import java.io.Serializable

sealed class AppError(
    override val message: String
) : Throwable(message), Serializable {
    object NoConnection : AppError("Sem conexão com a internet")
    object ServiceUnavailable : AppError("Serviço temporariamente indisponível")
    object Unknown : AppError("Ocorreu um erro inesperado")
    
}