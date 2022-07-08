package com.example.giftcardshop.domain.domain_repository

interface AuthenticationRepository {
    suspend fun login(username: String, password: String): Boolean
    suspend fun logout(): Boolean
}