package br.leandro.core.network.error

sealed class NetworkError : Throwable() {
    object NoConnection : NetworkError()
    object ServiceUnavailable : NetworkError()
    object Unknown : NetworkError()
}
