package com.example.giftcardshop.data.data_repository

import com.example.giftcardshop.domain.domain_repository.AuthenticationRepository
import com.example.giftcardshop.data.fake_apis.FakeAuthenticationApi
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: FakeAuthenticationApi,
) : AuthenticationRepository {
    override suspend fun login(username: String, password: String): Boolean {
        return authApi.requestLogin(username, password)
    }

    override suspend fun logout(): Boolean {
        return authApi.requestLogout()
    }
}