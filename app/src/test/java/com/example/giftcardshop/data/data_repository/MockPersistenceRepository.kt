package com.example.giftcardshop.data.data_repository

import com.example.giftcardshop.domain.domain_repository.PersistenceRepository
import com.example.giftcardshop.domain.model.AuthStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockPersistenceRepository : PersistenceRepository {
    private var dataStoreAuthStatus: AuthStatus? = null

    override suspend fun persistAuthStatus(authStatus: AuthStatus) {
        dataStoreAuthStatus = authStatus
    }

    override suspend fun clearPersistence() {
        dataStoreAuthStatus = null
    }

    override suspend fun getAuthStatus(): Flow<AuthStatus> {
        return flow {
            emit(dataStoreAuthStatus ?: AuthStatus(
                username = null,
                password = null,
                authStatus = false
            ))
        }
    }

    // Accessor
    fun authStatus() : AuthStatus {
        return dataStoreAuthStatus ?: AuthStatus(
            username = null,
            password = null,
            authStatus = false
        )
    }

    fun changeAuthStatus(authStatus: AuthStatus) {
        dataStoreAuthStatus = authStatus
    }

    fun setDataStoreAuthStatus(authStatus: AuthStatus) {
        dataStoreAuthStatus = authStatus
    }
}