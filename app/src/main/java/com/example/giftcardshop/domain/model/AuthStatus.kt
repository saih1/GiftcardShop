package com.example.giftcardshop.domain.model

data class AuthStatus(
    val username: String?,
    val password: String?,
    val authStatus: Boolean
)
