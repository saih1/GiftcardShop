package com.example.giftcardshop.domain.use_case

import com.example.giftcardshop.domain.domain_repository.PersistenceRepository
import com.example.giftcardshop.domain.model.AuthStatus
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetPersistenceAuthUseCase @Inject constructor(
    private val persistenceRepository: PersistenceRepository,
) {
    suspend fun doAction(): AuthStatus {
        return persistenceRepository.getAuthStatus().first()
    }
}