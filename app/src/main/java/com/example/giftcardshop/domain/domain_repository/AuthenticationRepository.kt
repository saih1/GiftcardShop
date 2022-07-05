package com.example.giftcardshop.domain.domain_repository

interface AuthenticationRepository {
    suspend fun signIn(username: String, password: String): Boolean
    suspend fun signOut(): Boolean
}