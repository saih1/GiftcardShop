package com.example.giftcardshop.data.data_repository

import com.example.giftcardshop.data.network.dto.GiftcardDto

interface GiftcardRepository {
    suspend fun getGiftcards(): List<GiftcardDto>
}