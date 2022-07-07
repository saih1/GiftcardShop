package com.example.giftcardshop.domain.domain_repository

import com.example.giftcardshop.domain.model.AuthStatus
import kotlinx.coroutines.flow.Flow

interface PersistenceRepository {
    suspend fun persistAuthStatus(authStatus: AuthStatus)
    suspend fun clearPersistence()
    suspend fun getAuthStatus(): Flow<AuthStatus>
}