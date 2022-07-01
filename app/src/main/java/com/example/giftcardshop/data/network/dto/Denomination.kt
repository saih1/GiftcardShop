package com.example.giftcardshop.data.network.dto

import java.io.Serializable

data class Denomination(
    val price: Double,
    val currency: String,
    val stock: String
) : Serializable