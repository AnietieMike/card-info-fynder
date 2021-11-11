package com.anietie.cardinfofynder.core.base

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>() // Status success and data of the result
    data class Error<F>(val errorMessage: F) :
        Result<F>() // Status Error an error message
    data class Failed(val failure: Failure) :
        Result<Failure>() // Status error and error message

    // string method to display a result for debugging
    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Failed -> "Success[data=$failure]"
            is Error<*> -> "Error[exception=$errorMessage]"
        }
    }
}
