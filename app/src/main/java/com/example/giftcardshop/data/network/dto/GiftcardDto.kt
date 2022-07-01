package com.example.giftcardshop.data.network.dto

import java.io.Serializable

data class GiftcardDto(
    val brand: String,
    val cardTypeStatus: String,
    val denominations: List<Denomination>,
    val disclaimer: String,
    val discount: Double,
    val id: String,
    val image: String,
    val importantContent: String,
    val position: Int,
    val terms: String,
    val vendor: String
) : Serializable