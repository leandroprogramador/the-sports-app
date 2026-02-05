package br.leandro.core.network.error

import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.cancellation.CancellationException

fun Throwable.toNetworkError(): NetworkError =
    when (this) {
        is SocketTimeoutException -> NetworkError.ServiceUnavailable
        is UnknownHostException,
        is ConnectException,
        is SocketException,
        is IOException -> NetworkError.NoConnection

        is HttpException -> {
            when (code()) {
                500, 502, 503 -> NetworkError.ServiceUnavailable
                else -> NetworkError.Unknown
            }
        }
        else -> NetworkError.Unknown
    }
