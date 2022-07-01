package com.example.giftcardshop.data.data_repository

import com.example.giftcardshop.data.network.GiftcardApi
import com.example.giftcardshop.data.network.dto.GiftcardDto
import javax.inject.Inject

class GiftcardRepositoryImpl @Inject constructor(
    private val giftcardApi: GiftcardApi
) : GiftcardRepository {
    override suspend fun getGiftcards(): List<GiftcardDto> {
        return giftcardApi.getGiftcards()
    }
}