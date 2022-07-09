package com.example.giftcardshop.domain.use_case

import com.example.giftcardshop.data.data_repository.MockAuthenticationRepository
import com.example.giftcardshop.data.data_repository.MockPersistenceRepository
import com.example.giftcardshop.domain.model.AuthStatus
import com.example.giftcardshop.shared.RequestState
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class LogoutUseCaseTest {

    private lateinit var logoutUseCase: LogoutUseCase
    private lateinit var mockAuthRepo: MockAuthenticationRepository
    private lateinit var mockPersistenceRepo: MockPersistenceRepository

    @Before
    fun setUp() {
        mockAuthRepo = MockAuthenticationRepository()
        mockPersistenceRepo = MockPersistenceRepository()
        logoutUseCase = LogoutUseCase(
            authenticationRepository = mockAuthRepo,
            persistenceRepository = mockPersistenceRepo
        )
    }

    @Test
    fun `successful logout request, returns false and clears persistence`() {
        runBlocking {
            mockAuthRepo.setLogoutReturnValue(true)
            val logoutResult = logoutUseCase.doAction().last()
            val logoutExpected = RequestState.success(false)
            val authStatusExpected = AuthStatus(
                username = null,
                password = null,
                authStatus = false
            )

            assertThat(logoutResult).isEqualTo(logoutExpected)
            assertThat(mockPersistenceRepo.authStatus()).isEqualTo(authStatusExpected)
        }
    }

    @Test
    fun `unsuccessful logout request, returns true and keep persistence`() {
        runBlocking {
            mockAuthRepo.setLogoutReturnValue(false)
            val addedAuthStatus = AuthStatus(
                username = "initialUsername",
                password = "initialPassword",
                true
            )
            mockPersistenceRepo.changeAuthStatus(addedAuthStatus)
            val logoutResult = logoutUseCase.doAction().last()
            val logoutExpected = RequestState.success(true)

            assertThat(logoutResult).isEqualTo(logoutExpected)
            assertThat(mockPersistenceRepo.authStatus()).isEqualTo(addedAuthStatus)
        }
    }

    @Test
    fun `network error, exception is handled`() {
        mockAuthRepo.throwException(true)
        runBlocking {
            val logoutResult = logoutUseCase.doAction().last()
            val logoutExpected = RequestState.error("logout error", null)

            assertThat(logoutResult).isEqualTo(logoutExpected)
        }
    }
}