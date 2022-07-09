package com.example.giftcardshop.domain.use_case

import com.example.giftcardshop.data.data_repository.MockGiftcardRepository
import com.example.giftcardshop.data.network.dto.GiftcardDto
import com.example.giftcardshop.domain.model.Giftcard
import com.example.giftcardshop.shared.RequestState
import com.example.giftcardshop.shared.toGiftcard
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class GetGiftcardsUseCaseTest {

    private lateinit var getGiftcardsUseCase: GetGiftcardsUseCase
    private lateinit var mockGiftCardRepo: MockGiftcardRepository

    private var giftCardDtoList = listOf(
        GiftcardDto(brand = "a", cardTypeStatus = "a", denominations = listOf(),
            disclaimer = "a", discount = 0.0, id = "a", image = "a",
            importantContent = "a", position = 0, terms = "a", vendor = "a"),
        GiftcardDto(brand = "b", cardTypeStatus = "b", denominations = listOf(),
            disclaimer = "b", discount = 0.0, id = "b", image = "b",
            importantContent = "b", position = 0, terms = "b", vendor = "b"))

    @Before
    fun setUp() {
        mockGiftCardRepo = MockGiftcardRepository()
        getGiftcardsUseCase = GetGiftcardsUseCase(mockGiftCardRepo)
    }

    @Test
    fun `Successful network request, returns list of GiftCardDto`() {
        runBlocking {
            mockGiftCardRepo.populateList(giftCardDtoList)
            val result: RequestState<List<Giftcard>> = getGiftcardsUseCase.doAction().last()
            val resultExpected = RequestState.success(giftCardDtoList.map(GiftcardDto::toGiftcard))

            assertThat(result).isEqualTo(resultExpected)
        }
    }

    @Test
    fun `Http exception, handles the exception`() {
        runBlocking {
            mockGiftCardRepo.throwHttpException(true)
            val result: RequestState<List<Giftcard>> = getGiftcardsUseCase.doAction().last()
            val resultExpected = RequestState.error("HTTP 500 Response.error()", null)

            assertThat(result).isEqualTo(resultExpected)
        }
    }

    @Test
    fun `IO exception, handles the exception`() {
        runBlocking {
            mockGiftCardRepo.throwIOException(true)
            val result: RequestState<List<Giftcard>> = getGiftcardsUseCase.doAction().last()
            val resultExpected = RequestState.error("IO Exception", null)

            assertThat(result).isEqualTo(resultExpected)
        }
    }
}