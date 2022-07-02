package com.example.giftcardshop.domain.domain_repository

import com.example.giftcardshop.data.network.dto.GiftcardDto

interface GiftcardRepository {
    suspend fun getGiftcards(): List<GiftcardDto>
}