package com.example.giftcardshop.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.giftcardshop.domain.model.CartItem
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.internal.toImmutableList
import org.junit.Before
import org.junit.Test

class CartItemDatabaseTest {

    private lateinit var cartItemDao: CartItemDao
    private lateinit var db: CartItemDatabase

    @Before
    fun setUp() {
        val ctx = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(ctx, CartItemDatabase::class.java).build()
        cartItemDao = db.cartItemDao()
    }

    @Test
    fun `getCartItem returns emptyList on initial state`() = runBlocking {
        // Arrange + Act
        val result = cartItemDao.getCartItems().first()

        // Assert
        assertThat(result).isEmpty()
    }

    @Test
    fun `insert and get`() = runBlocking {
        // Arrange
        val cartItem = createTestCartItem(id = 1)

        // Act
        cartItemDao.addCartItem(cartItem)
        val result: List<CartItem> = cartItemDao.getCartItems().first()

        // Assert
        assertThat(result.first()).isEqualTo(cartItem)
    }

    @Test
    fun `insert many entities and get and check`() = runBlocking {
        // Arrange
        val cartItems = populateTestCartItems(size = 10)

        // Act
        cartItems.forEach { cartItemDao.addCartItem(it) }
        val result: List<CartItem> = cartItemDao.getCartItems().first()

        // Assert
        result.forEachIndexed { index, cartItem ->
            assertThat(cartItem.id).isEqualTo(index + 1)
        }
    }
}

fun populateTestCartItems(size: Int) : List<CartItem> {
    val result = mutableListOf<CartItem>()
    repeat(size) { result.add(createTestCartItem(it)) }
    return result.toImmutableList()
}

fun createTestCartItem(id: Int) = CartItem(
    id = id,
    brand = "testBrand",
    value = 10.0,
    image = "",
    vendor = "testVendor",
    payable = 0.0,
    quantity = 1,
    totalPayable = 10.0
)