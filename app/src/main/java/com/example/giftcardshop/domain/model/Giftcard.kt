package com.example.giftcardshop.domain.model

data class Giftcard(
    val image: String,
    val brand: String,
    val discount: Double,
    val terms: String,
    val denomination: List<Denomination>,
    val vendor: String
)


