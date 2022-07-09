package com.example.giftcardshop.domain.use_case

import com.example.giftcardshop.data.data_repository.MockCartRepository
import org.junit.Assert.*

import org.junit.Before

class AddCartItemUseCaseTest {

    private lateinit var addCartItemUseCase: AddCartItemUseCase
    private lateinit var mockCartRepo: MockCartRepository

    @Before
    fun setUp() {
        mockCartRepo = MockCartRepository()
        addCartItemUseCase = AddCartItemUseCase(mockCartRepo)
    }


}