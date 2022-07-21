package com.example.giftcardshop.data.data_repository

import com.example.giftcardshop.domain.domain_repository.CartRepository
import com.example.giftcardshop.domain.model.CartItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockCartRepository : CartRepository {
    private val mockDatabase: MutableList<CartItem> = mutableListOf()
    private var throwException = false

    override fun getCartItems(): Flow<List<CartItem>> {
        return flow {
            if (throwException) throw Exception("Database error")
            emit(mockDatabase)
        }
    }

    override suspend fun addCartItem(cartItem: CartItem) {
        mockDatabase.add(cartItem)
    }

    override suspend fun deleteCartItem(cartItem: CartItem) {
        mockDatabase.remove(cartItem)
    }

    override suspend fun clearCartItems() {
        mockDatabase.clear()
    }

    override suspend fun getCartItemByBrandAndValue(cartItem: CartItem): Flow<CartItem?> {
        val existingItem: CartItem? = mockDatabase.find {
            it.brand == cartItem.brand && it.value.equals(cartItem.value)
        }
        return flow { emit(existingItem) }
    }

    override suspend fun updateCartItem(cartItem: CartItem) {
        mockDatabase.removeIf { it.brand == cartItem.brand
                && it.value == cartItem.value
        }
        mockDatabase.add(cartItem)
    }

    // Accessor
    fun throwException(boolean: Boolean) {
        throwException = boolean
    }

    fun populateMockDatabase(inputList: List<CartItem>) {
        mockDatabase.addAll(inputList)
    }

    fun getCount(): Int = mockDatabase.count()

    fun findCartInDatabase(cartItem: CartItem): Boolean = mockDatabase.contains(cartItem)
}