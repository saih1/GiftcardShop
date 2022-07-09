package com.example.giftcardshop.domain.use_case

import com.example.giftcardshop.data.data_repository.MockCartRepository
import com.example.giftcardshop.domain.model.CartItem
import com.example.giftcardshop.shared.RequestState
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetCartItemsUseCaseTest {

    private lateinit var getCartItemsUseCase: GetCartItemsUseCase
    private lateinit var mockCartRepo: MockCartRepository

    private var mockListOfCarts = listOf(
        CartItem(brand = "A", value = 0.0, image = "A", vendor = "A"),
        CartItem(brand = "B", value = 1.0, image = "B", vendor = "B"),
        CartItem(brand = "C", value = 2.0, image = "C", vendor = "C"),
        CartItem(brand = "D", value = 3.0, image = "D", vendor = "D")
    )

    @Before
    fun setUp() {
        mockCartRepo = MockCartRepository()
        getCartItemsUseCase = GetCartItemsUseCase(mockCartRepo)
    }

    @Test
    fun `Successful request, returns a list of cart items`() {
        runBlocking {
            mockCartRepo.populateMockDatabase(mockListOfCarts)
            val listResult = getCartItemsUseCase.doAction().last()
            val listExpected = RequestState.success(mockListOfCarts)

            assertThat(listResult).isEqualTo(listExpected)
        }
    }

    @Test
    fun `Unsuccessful request, handles exception`() {
        runBlocking {
            mockCartRepo.throwException(true)
            val result = getCartItemsUseCase.doAction().last()
            val expected = RequestState.error("Database error", null)

            assertThat(result).isEqualTo(expected   )
        }
    }
}