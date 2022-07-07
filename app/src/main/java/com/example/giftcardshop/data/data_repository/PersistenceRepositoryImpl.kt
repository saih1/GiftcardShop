package com.example.giftcardshop.data.data_repository

import com.example.giftcardshop.data.local.AuthDataStore
import com.example.giftcardshop.domain.domain_repository.PersistenceRepository
import com.example.giftcardshop.domain.model.AuthStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PersistenceRepositoryImpl @Inject constructor(
    private val authDataStore: AuthDataStore
): PersistenceRepository {
    override suspend fun persistAuthStatus(authStatus: AuthStatus) {
        authDataStore.persistAuthStatus(authStatus)
    }

    override suspend fun clearPersistence() {
        authDataStore.clearAuthPersistence()
    }

    override suspend fun getAuthStatus(): Flow<AuthStatus> {
        return authDataStore.getCurrentAuthStatus
    }
}