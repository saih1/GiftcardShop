package com.example.giftcardshop.data.data_repository

import com.example.giftcardshop.data.network.dto.GiftcardDto
import com.example.giftcardshop.domain.domain_repository.GiftcardRepository

class MockGiftcardRepository : GiftcardRepository {
    override suspend fun getGiftcards(): List<GiftcardDto> {
        TODO("Not yet implemented")
    }
}