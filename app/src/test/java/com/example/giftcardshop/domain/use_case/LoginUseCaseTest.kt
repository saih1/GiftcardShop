package com.example.giftcardshop.domain.use_case

import com.example.giftcardshop.data.data_repository.MockAuthenticationRepository
import com.example.giftcardshop.data.data_repository.MockPersistenceRepository
import com.example.giftcardshop.domain.model.AuthStatus
import com.example.giftcardshop.shared.RequestState
import com.google.common.truth.Truth.*
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class LoginUseCaseTest {

    private lateinit var loginUseCase: LoginUseCase
    private lateinit var mockAuthRepo: MockAuthenticationRepository
    private lateinit var mockPersistenceRepo: MockPersistenceRepository

    @Before
    fun setUp() {
        mockAuthRepo = MockAuthenticationRepository()
        mockPersistenceRepo = MockPersistenceRepository()
        loginUseCase = LoginUseCase(
            authenticationRepository = mockAuthRepo,
            persistenceRepository = mockPersistenceRepo
        )
    }

    @Test
    fun `Correct credentials, returns true and persist data`() {
        runBlocking {
            val loginResult: RequestState<Boolean> = loginUseCase
                .doAction(
                    "correctUsername",
                    "correctPassword"
                ).last()
            val loginExpected = RequestState.success(true)
            val authStatusExpected = AuthStatus(
                username = "correctUsername",
                password = "correctPassword",
                authStatus = true
            )

            assertThat(loginResult).isEqualTo(loginExpected)
            assertThat(mockPersistenceRepo.authStatus()).isEqualTo(authStatusExpected)
        }
    }

    @Test
    fun `Incorrect credentials, returns false and no persistence`() {
        runBlocking {
            val loginResult = loginUseCase.doAction(
                username = "wrongUsername",
                password = "wrongPassword",
            ).last()
            val loginExpected = RequestState.success(false)
            val authStatusExpected = AuthStatus(
                username = null,
                password = null,
                authStatus = false
            )

            assertThat(loginResult).isEqualTo(loginExpected)
            assertThat(mockPersistenceRepo.authStatus()).isEqualTo(authStatusExpected)
        }
    }

    @Test
    fun `Empty credentials, throws Exception`() {
            try {
                loginUseCase.doAction("", "")
            } catch (e: Exception) {

                assertThat(e).also {
                    it.isInstanceOf(Exception::class.java)
                    it.hasMessageThat().isEqualTo("Empty credentials")
                }
            }
    }
}