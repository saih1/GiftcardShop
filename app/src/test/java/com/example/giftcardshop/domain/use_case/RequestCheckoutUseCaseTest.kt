package com.example.giftcardshop.domain.use_case

import com.example.giftcardshop.data.data_repository.MockCartRepository
import com.example.giftcardshop.data.data_repository.MockCheckoutRepository
import com.example.giftcardshop.domain.model.CartItem
import com.example.giftcardshop.shared.RequestState
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class RequestCheckoutUseCaseTest {

    private lateinit var checkoutUseCase: RequestCheckoutUseCase
    private lateinit var mockCheckoutRepo: MockCheckoutRepository
    private lateinit var mockCartRepo: MockCartRepository

    private var mockListOfCarts = listOf(
        CartItem(brand = "A", value = 0.0, image = "A", vendor = "A"),
        CartItem(brand = "B", value = 1.0, image = "B", vendor = "B"),
        CartItem(brand = "C", value = 2.0, image = "C", vendor = "C"),
        CartItem(brand = "D", value = 3.0, image = "D", vendor = "D")
    )

    @Before
    fun setUp() {
        mockCheckoutRepo = MockCheckoutRepository()
        mockCartRepo = MockCartRepository()
        checkoutUseCase = RequestCheckoutUseCase(
            checkoutRepository = mockCheckoutRepo,
            cartRepository = mockCartRepo,
        )
    }

    @Test
    fun `Successful checkout request returns true and clears cart database`() {
        runBlocking {
            val totalAmount = mockListOfCarts.sumOf { it.value }
            mockCartRepo.populateMockDatabase(mockListOfCarts)
            val result = checkoutUseCase.doAction(totalAmount).last()
            val resultExpected = RequestState.success(true)

            assertThat(result).isEqualTo(resultExpected)
            assertThat(mockCartRepo.getCount()).isEqualTo(0)
        }
    }

    @Test
    fun `Unsuccessful checkout request returns false and keeps cart database`() {
        runBlocking {
            val totalAmount = mockListOfCarts.sumOf { it.value }
            val expectedCartCount = mockListOfCarts.count()

            mockCartRepo.populateMockDatabase(mockListOfCarts)

            mockCheckoutRepo.checkoutReturnBoolean(false)
            val result = checkoutUseCase.doAction(totalAmount).last()
            val resultExpected = RequestState.success(false)

            assertThat(result).isEqualTo(resultExpected)
            assertThat(mockCartRepo.getCount()).isEqualTo(expectedCartCount)
        }
    }

    @Test
    fun `Checkout exception, handles exception`() {
        runBlocking {
            mockCheckoutRepo.throwException(true)
            val result = checkoutUseCase.doAction(10.0).last()
            val resultExpected = RequestState.error("Checkout error", null)

            assertThat(result).isEqualTo(resultExpected)
        }
    }

    @Test
    fun `Invalid amount, throws Exception`() {
        runBlocking {
            val result = runCatching {
                checkoutUseCase.doAction(-10.0).last()
            }.onFailure { assertThat(it).isInstanceOf(Exception::class.java) }

            assertThat(result.isFailure).isTrue()
            assertThat(result.exceptionOrNull()?.message).isEqualTo("Invalid amount")
        }
    }
}