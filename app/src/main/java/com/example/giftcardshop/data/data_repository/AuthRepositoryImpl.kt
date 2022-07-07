package com.example.giftcardshop.data.data_repository

import com.example.giftcardshop.domain.domain_repository.AuthenticationRepository
import com.example.giftcardshop.data.fake_apis.FakeAuthenticationApi
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: FakeAuthenticationApi,
) : AuthenticationRepository {
    override suspend fun signIn(username: String, password: String): Boolean {
        return authApi.signIn(username, password)
    }

    override suspend fun signOut(): Boolean {
        return authApi.signOut()
    }
}