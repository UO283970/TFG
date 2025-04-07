package com.example.tfg.repository

import com.example.tfg.repository.exceptions.AuthenticationException

object GlobalErrorHandler {
    var onAuthError: (() -> Unit)? = null
    var onOtherError: ((String) -> Unit)? = null

    fun handle(error: Throwable) {
        when (error) {
            is AuthenticationException -> onAuthError?.invoke()
            else -> onOtherError?.invoke("Error inesperado")
        }
    }
}