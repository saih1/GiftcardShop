package com.example.giftcardshop.data.local

import androidx.room.*
import com.example.giftcardshop.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartItemDao {
    @Query("SELECT * FROM cart_table")
    fun getCartItems(): Flow<List<CartItem>>

    @Query("SELECT * FROM cart_table WHERE id = :id")
    fun getCartItemById(id: Int): Flow<CartItem>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCartItem(cartItem: CartItem)

    @Delete
    suspend fun deleteCartItem(cartItem: CartItem)

    @Query("DELETE FROM cart_table")
    suspend fun clearCartItems()
}