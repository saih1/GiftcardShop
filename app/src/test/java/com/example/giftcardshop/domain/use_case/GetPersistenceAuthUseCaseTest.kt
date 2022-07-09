package com.example.giftcardshop.domain.use_case

import com.example.giftcardshop.data.data_repository.MockPersistenceRepository
import com.example.giftcardshop.domain.model.AuthStatus
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class GetPersistenceAuthUseCaseTest {

    private lateinit var mockPersistenceRepo: MockPersistenceRepository
    private lateinit var getPersistenceAuthUseCase: GetPersistenceAuthUseCase

    @Before
    fun setUp() {
        mockPersistenceRepo = MockPersistenceRepository()
        getPersistenceAuthUseCase = GetPersistenceAuthUseCase(mockPersistenceRepo)
    }

    @Test
    fun `Gets AuthStatus stored in Local Persistence`() {
        runBlocking {
            val authStatusToAdd = AuthStatus("username","password",true)
            mockPersistenceRepo.setDataStoreAuthStatus(authStatus = authStatusToAdd)

            val result: AuthStatus = getPersistenceAuthUseCase.doAction()

            assertThat(result).isEqualTo(authStatusToAdd)
        }
    }
}