package com.example.giftcardshop.data.network

import com.example.giftcardshop.data.network.dto.GiftcardDto
import retrofit2.http.GET

interface GiftcardApi {
    @GET("giftcards/")
    suspend fun getGiftcards(): List<GiftcardDto>
}