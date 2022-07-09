package com.example.giftcardshop.data.data_repository

import com.example.giftcardshop.data.network.dto.GiftcardDto
import com.example.giftcardshop.domain.domain_repository.GiftcardRepository
import kotlinx.coroutines.delay
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class MockGiftcardRepository : GiftcardRepository {
    private var throwHttpException = false
    private var throwIOException = false

    private var giftCardDtoList = mutableListOf<GiftcardDto>()

    override suspend fun getGiftcards(): List<GiftcardDto> {
        delay(200)
        if (throwHttpException) {
            throw HttpException(Response.error<Any>(500,
                "Http Exception".toResponseBody("text/plain".toMediaTypeOrNull())))
        }
        if (throwIOException) {
            throw IOException("IO Exception")
        }
        return giftCardDtoList
    }

    // Accessor
    fun populateList(list: List<GiftcardDto>) {
        giftCardDtoList.addAll(list)
    }

    fun throwHttpException(boolean: Boolean) {
        throwHttpException = boolean
    }

    fun throwIOException(boolean: Boolean) {
        throwIOException = boolean
    }
}