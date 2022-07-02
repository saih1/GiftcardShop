package com.example.giftcardshop.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.giftcardshop.domain.model.CartItem

@Database(entities = [CartItem::class], version = 1, exportSchema = false)
abstract class CartItemDatabase : RoomDatabase() {
    abstract fun cartItemDao(): CartItemDao
}