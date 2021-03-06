package com.bsuir.domain.error

sealed class ApplicationError {

    // Common errors

    object Generic : ApplicationError()
    object NoInternetConnection : ApplicationError()

    // Http errors

    object BadRequest : ApplicationError()
    object Unauthorized : ApplicationError()
    object NotFound : ApplicationError()
    object TimeOut : ApplicationError()
    object Server : ApplicationError()

    // Client validation errors

    object EmptyFiled : ApplicationError()
}