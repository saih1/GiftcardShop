package com.example.giftcardshop.domain.use_case

import com.example.giftcardshop.data.data_repository.MockCartRepository
import com.example.giftcardshop.domain.model.Giftcard
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class AddCartItemUseCaseTest {

    private lateinit var addCartItemUseCase: AddCartItemUseCase
    private lateinit var mockCartRepo: MockCartRepository

    @Before
    fun setUp() {
        mockCartRepo = MockCartRepository()
        addCartItemUseCase = AddCartItemUseCase(mockCartRepo)
    }

    @Test
    fun `Adding cart item, successfully stores the cart item in the database`() {
        runBlocking {
            val giftcardToAdd = Giftcard(image = "",
                brand = "",
                discount = 0.0,
                terms = "",
                denominations = listOf(),
                vendor = "")
            // Before
            assertThat(mockCartRepo.getCount()).isEqualTo(0)

            addCartItemUseCase.doAction(giftcardToAdd, 10.0)

            // After
            assertThat(mockCartRepo.getCount()).isEqualTo(1)
        }
    }
}