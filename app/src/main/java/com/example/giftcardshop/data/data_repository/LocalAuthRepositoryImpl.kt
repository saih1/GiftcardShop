package com.example.giftcardshop.data.data_repository

import com.example.giftcardshop.domain.domain_repository.AuthenticationRepository
import com.example.giftcardshop.shared.FakeAuthenticator
import javax.inject.Inject

class LocalAuthRepositoryImpl @Inject constructor(
    private val fakeAuthenticator: FakeAuthenticator,
) : AuthenticationRepository {
    override suspend fun signIn(username: String, password: String): Boolean {
        return fakeAuthenticator.signIn(username, password)
    }

    override suspend fun signOut(): Boolean {
        return fakeAuthenticator.signOut()
    }
}