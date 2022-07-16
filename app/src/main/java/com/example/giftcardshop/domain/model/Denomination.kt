package com.example.giftcardshop.domain.model

data class Denomination(
    val price: Double,
    val currency: String,
    val stock: String,
    val payable: Double
)
