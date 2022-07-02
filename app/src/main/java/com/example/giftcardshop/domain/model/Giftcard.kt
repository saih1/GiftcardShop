package com.example.giftcardshop.domain.model

import com.example.giftcardshop.data.network.dto.Denomination

data class Giftcard(
    val image: String,
    val brand: String,
    val discount: Double,
    val terms: String,
    val denominations: List<Denomination>,
    val vendor: String
)


