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

    private val mockListOfCarts = listOf(
        CartItem(brand = "A", value = 0.0, image = "A", vendor = "A",
            payable = 0.1, totalPayable = 0.1, quantity = 1),
        CartItem(brand = "B", value = 1.0, image = "B", vendor = "B",
            payable = 0.2, totalPayable = 0.2, quantity = 1),
        CartItem(brand = "C", value = 2.0, image = "C", vendor = "C",
            payable = 0.3, totalPayable = 0.3, quantity = 1),
        CartItem(brand = "D", value = 3.0, image = "D", vendor = "D",
            payable = 0.4, totalPayable = 0.4, quantity = 1)
    )

    private val invalidListOfCarts = listOf(
        CartItem(brand = "A", value = 0.0, image = "A", vendor = "A", payable = -1.0, totalPayable = -1.0, quantity = 1)
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
            mockCartRepo.populateMockDatabase(mockListOfCarts)
            val result = checkoutUseCase.doAction(mockListOfCarts).last()
            val resultExpected = RequestState.success(true)

            assertThat(result).isEqualTo(resultExpected)
            assertThat(mockCartRepo.getCount()).isEqualTo(0)
        }
    }

    @Test
    fun `Unsuccessful checkout request returns false and keeps cart database`() {
        runBlocking {
            val expectedCartCount = mockListOfCarts.count()

            mockCartRepo.populateMockDatabase(mockListOfCarts)

            mockCheckoutRepo.checkoutReturnBoolean(false)
            val result = checkoutUseCase.doAction(mockListOfCarts).last()
            val resultExpected = RequestState.success(false)

            assertThat(result).isEqualTo(resultExpected)
            assertThat(mockCartRepo.getCount()).isEqualTo(expectedCartCount)
        }
    }

    @Test
    fun `Checkout exception, handles exception`() {
        runBlocking {
            mockCheckoutRepo.throwException(true)
            val result = checkoutUseCase.doAction(mockListOfCarts).last()
            val resultExpected = RequestState.error("Checkout error", null)

            assertThat(result).isEqualTo(resultExpected)
        }
    }

    @Test
    fun `Invalid amount, throws Exception`() {
        runBlocking {
            val result = runCatching {
                checkoutUseCase.doAction(invalidListOfCarts).last()
            }.onFailure { assertThat(it).isInstanceOf(Exception::class.java) }

            assertThat(result.isFailure).isTrue()
            assertThat(result.exceptionOrNull()?.message).isEqualTo("Invalid total amount")
        }
    }
}