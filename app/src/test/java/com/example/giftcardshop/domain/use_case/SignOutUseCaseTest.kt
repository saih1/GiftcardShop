package com.example.giftcardshop.domain.use_case

import com.example.giftcardshop.data.data_repository.MockAuthenticationRepository
import com.example.giftcardshop.data.data_repository.MockPersistenceRepository
import org.junit.Assert.*

import org.junit.Before

class SignOutUseCaseTest {

    private lateinit var signInUseCase: SignInUseCase
    private lateinit var mockAuthRepo: MockAuthenticationRepository
    private lateinit var mockPersistenceRepo: MockPersistenceRepository

    @Before
    fun setUp() {
        mockAuthRepo = MockAuthenticationRepository()
        mockPersistenceRepo = MockPersistenceRepository()
        signInUseCase = SignInUseCase(
            authenticationRepository = mockAuthRepo,
            persistenceRepository = mockPersistenceRepo
        )
    }
}