package com.anietie.cardinfofynder.core.state

import java.lang.Exception

/**
 * Generic class for holding success response, error response and loading status
 */
data class ResponseState<out T>(
    val status: Status,
    val data: T?,
    val error: Exception?,
    var message: String?
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
    }

    companion object {
        fun <T> success(data: T?): ResponseState<T> {
            return ResponseState(Status.SUCCESS, data, null, null)
        }

        fun <T> error(message: String, error: Exception?): ResponseState<Nothing> {
            return ResponseState(Status.ERROR, null, error, message)
        }

        fun <T> loading(data: T? = null): ResponseState<T> {
            return ResponseState(Status.LOADING, data, null, null)
        }
    }
}
