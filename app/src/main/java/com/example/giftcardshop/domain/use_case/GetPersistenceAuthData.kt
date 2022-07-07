package com.example.giftcardshop.domain.use_case

import com.example.giftcardshop.data.local.AuthDataStore
import com.example.giftcardshop.domain.model.AuthStatus
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetPersistenceAuthData @Inject constructor(
    private val authDataStore: AuthDataStore
) {
    suspend fun doAction(): AuthStatus {
        return authDataStore.getCurrentAuthStatus.first()
    }
}