package com.example.giftcardshop.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val brand: String,
    val value: Double,
    val image: String,
    val vendor: String
)
