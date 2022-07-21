package com.example.giftcardshop.domain.use_case

import com.example.giftcardshop.data.data_repository.MockCheckoutRepository
import com.example.giftcardshop.domain.model.Denomination
import com.example.giftcardshop.domain.model.Giftcard
import com.example.giftcardshop.shared.RequestState
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class RequestBuyNowUseCaseTest {

    private lateinit var buyNowUseCase: RequestBuyNowUseCase
    private lateinit var checkoutRepo: MockCheckoutRepository

    @Before
    fun setUp() {
        checkoutRepo = MockCheckoutRepository()
        buyNowUseCase = RequestBuyNowUseCase(checkoutRepo)
    }

    @Test
    fun `Successful buy request returns true`() {
        val giftcard = Giftcard(
            image = "", brand = "", discount = 90.0,
            terms = "", vendor = "", denomination = listOf(
                Denomination(price = 20.0, currency = "", stock = "", payable = 18.0),
                Denomination(price = 50.0, currency = "", stock = "", payable = 45.0)
            ),
        )
        val selectedDenomination = giftcard.denomination[0]

        runBlocking {
            val result = buyNowUseCase.doAction(
                giftcard, selectedDenomination
            ).last()
            val resultExpected = RequestState.success(true)

            Truth.assertThat(result).isEqualTo(resultExpected)
        }
    }

    @Test
    fun `Unsuccessful buy request returns false`() {
        val giftcard = Giftcard(
            image = "", brand = "", discount = 90.0,
            terms = "", vendor = "", denomination = listOf(
                Denomination(price = 20.0, currency = "", stock = "", payable = 18.0),
                Denomination(price = 50.0, currency = "", stock = "", payable = 45.0)
            ),
        )
        val selectedDenomination = giftcard.denomination[0]

        runBlocking {
            checkoutRepo.checkoutReturnBoolean(false)

            val result = buyNowUseCase.doAction(
                giftcard, selectedDenomination
            ).last()
            val resultExpected = RequestState.success(false)

            Truth.assertThat(result).isEqualTo(resultExpected)
        }
    }

    @Test
    fun `Buy now request exception, handles exception`() {
        val giftcard = Giftcard(
            image = "", brand = "", discount = 90.0,
            terms = "", vendor = "", denomination = listOf(
                Denomination(price = 20.0, currency = "", stock = "", payable = 18.0),
                Denomination(price = 50.0, currency = "", stock = "", payable = 45.0)
            ),
        )
        val selectedDenomination = giftcard.denomination[0]

        runBlocking {
            checkoutRepo.throwException(true)
            val result = buyNowUseCase.doAction(
                giftcard, selectedDenomination
            ).last()
            val resultExpected = RequestState.error("Checkout error", null)

            Truth.assertThat(result).isEqualTo(resultExpected)
        }
    }

    @Test
    fun `Invalid amount BuyNow request, throws Exception`() {
        val giftcard = Giftcard(
            image = "", brand = "", discount = 90.0,
            terms = "", vendor = "", denomination = listOf(
                Denomination(price = 20.0, currency = "", stock = "", payable = 0.0),
                Denomination(price = 50.0, currency = "", stock = "", payable = 45.0)
            ),
        )
        val selectedDenomination = giftcard.denomination[0]

        runBlocking {
            val result = runCatching {
                buyNowUseCase.doAction(
                    giftcard, selectedDenomination
                ).last()
            }.onFailure { Truth.assertThat(it).isInstanceOf(Exception::class.java) }

            Truth.assertThat(result.isFailure).isTrue()
            Truth.assertThat(result.exceptionOrNull()?.message).isEqualTo("Invalid payable amount")
        }
    }

}