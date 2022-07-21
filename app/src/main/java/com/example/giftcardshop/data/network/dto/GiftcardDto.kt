package com.example.giftcardshop.data.network.dto

import com.squareup.moshi.Json
import java.io.Serializable

data class GiftcardDto(
    val brand: String,
    val cardTypeStatus: String,
    @Json(name = "denominations")
    val denominationDtoList: List<DenominationDto>,
    val disclaimer: String,
    val discount: Double,
    val id: String,
    val image: String,
    val importantContent: String,
    val position: Int,
    val terms: String,
    val vendor: String
) : Serializable