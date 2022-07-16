package com.example.giftcardshop.domain.use_case

import com.example.giftcardshop.data.data_repository.MockCartRepository
import com.example.giftcardshop.domain.model.Denomination
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
            val giftcardToAdd = Giftcard(
                image = "", brand = "", discount = 90.0,
                terms = "", vendor = "", denomination = listOf(
                    Denomination(price = 20.0, currency = "", stock = "", payable = 18.0),
                    Denomination(price = 50.0, currency = "", stock = "", payable = 45.0)
                ),
            )
            val selectedDenomination = giftcardToAdd.denomination[0]

            // Before
            assertThat(mockCartRepo.getCount()).isEqualTo(0)

            addCartItemUseCase.doAction(giftcardToAdd, selectedDenomination)

            // After
            assertThat(mockCartRepo.getCount()).isEqualTo(1)
        }
    }
}