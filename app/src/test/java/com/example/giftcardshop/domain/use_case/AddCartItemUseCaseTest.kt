package com.example.giftcardshop.domain.use_case

import com.example.giftcardshop.data.data_repository.MockCartRepository
import com.example.giftcardshop.domain.model.CartItem
import com.example.giftcardshop.domain.model.Denomination
import com.example.giftcardshop.domain.model.Giftcard
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class AddCartItemUseCaseTest {

    private lateinit var addCartItemUseCase: AddCartItemUseCase
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

    @Before
    fun setUp() {
        mockCartRepo = MockCartRepository()
        addCartItemUseCase = AddCartItemUseCase(mockCartRepo)
    }

    @Test
    fun `Adding cart item to empty database, successfully stores the cart item in the database`() {
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

    @Test
    fun `Adding an existing cart item, updates the existing items in the database with new values`() {
        runBlocking {

            val existingCartItemInDatabase = CartItem(
                id=0, brand="brand", value=20.0,
                image="image", vendor="vendor", payable=18.0,
                quantity=1, totalPayable=18.0
            )

            mockCartRepo.populateMockDatabase(
                mockListOfCarts.plus(existingCartItemInDatabase)
            )

            val giftcardToAdd = Giftcard(
                image = "image", brand = "brand", discount = 90.0,
                terms = "terms", vendor = "vendor",
                denomination = listOf(
                    Denomination(price = 20.0, currency = "", stock = "", payable = 18.0),
                    Denomination(price = 50.0, currency = "", stock = "", payable = 45.0)
                )
            )
            val selectedDenomination = giftcardToAdd.denomination[0]

            val expectedCartItem = CartItem(
                id = 0, brand = "brand", value = 20.0,
                image = "image", vendor = "vendor",
                payable = 18.0, quantity = 2,
                totalPayable = 18.0 * 2
            )
            addCartItemUseCase.doAction(giftcardToAdd, selectedDenomination)

            assertThat(mockCartRepo.findCartInDatabase(existingCartItemInDatabase))
                .isEqualTo(false)
            assertThat(mockCartRepo.findCartInDatabase(expectedCartItem))
                .isEqualTo(true)
        }
    }

    @Test
    fun `Adding a non-existing cart item to populated database, successfully adds the cart item`() {
        runBlocking {
            mockCartRepo.populateMockDatabase(mockListOfCarts)

            val giftcardToAdd = Giftcard(
                image = "image", brand = "brand", discount = 90.0,
                terms = "terms", vendor = "vendor",
                denomination = listOf(
                    Denomination(price = 20.0, currency = "", stock = "", payable = 18.0),
                    Denomination(price = 50.0, currency = "", stock = "", payable = 45.0)
                )
            )
            val selectedDenomination = giftcardToAdd.denomination[0]

            val expectedCartItem = CartItem(
                id = 0, brand = "brand", value = 20.0,
                image = "image", vendor = "vendor",
                payable = 18.0, quantity = 1,
                totalPayable = 18.0
            )
            addCartItemUseCase.doAction(giftcardToAdd, selectedDenomination)

            assertThat(mockCartRepo.findCartInDatabase(expectedCartItem))
                .isEqualTo(true)
        }
    }
}
