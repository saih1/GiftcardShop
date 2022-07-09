package com.example.giftcardshop.domain.use_case

import com.example.giftcardshop.data.data_repository.MockCartRepository
import com.example.giftcardshop.domain.model.CartItem
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class DeleteCartItemUseCaseTest {

    private lateinit var deleteCartItemUseCase: DeleteCartItemUseCase
    private lateinit var mockCartRepo: MockCartRepository

    private val mockListOfCarts = listOf(
        CartItem(brand = "A", value = 0.0, image = "A", vendor = "A"),
        CartItem(brand = "B", value = 1.0, image = "B", vendor = "B"),
        CartItem(brand = "C", value = 2.0, image = "C", vendor = "C"),
        CartItem(brand = "D", value = 3.0, image = "D", vendor = "D")
    )

    @Before
    fun setUp() {
        mockCartRepo = MockCartRepository()
        deleteCartItemUseCase = DeleteCartItemUseCase(mockCartRepo)
    }

    @Test
    fun `Deleting cart item, successfully deletes from the database`() {
        runBlocking {
            mockCartRepo.populateMockDatabase(mockListOfCarts)

            mockCartRepo.deleteCartItem(mockListOfCarts.first())

            assertThat(mockCartRepo.findCartInDatabase(mockListOfCarts.first()))
                .isEqualTo(false)
        }
    }
}