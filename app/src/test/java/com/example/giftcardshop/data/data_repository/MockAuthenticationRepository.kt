package com.example.giftcardshop.data.data_repository

import com.example.giftcardshop.domain.domain_repository.AuthenticationRepository

class MockAuthenticationRepository : AuthenticationRepository {
    private var logoutReturnValue = true
    private var throwException = false

    override suspend fun login(username: String, password: String): Boolean {
        if (throwException) throw Exception("Login error")
        return username == "correctUsername" && password == "correctPassword"
    }

    override suspend fun logout(): Boolean {
        if (throwException) throw Exception("logout error")
        return logoutReturnValue
    }

    // Accessor Methods
    fun setLogoutReturnValue(boolean: Boolean) {
        logoutReturnValue = boolean
    }

    fun throwException(boolean: Boolean) {
        throwException = boolean
    }
}