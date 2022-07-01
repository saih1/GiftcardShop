package com.example.giftcardshop.domain.use_case

import com.example.giftcardshop.data.data_repository.GiftcardRepository
import com.example.giftcardshop.data.network.dto.GiftcardDto
import com.example.giftcardshop.shared.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetGiftcardsUseCase @Inject constructor(
    private val repo: GiftcardRepository
) {
    fun doAction(): Flow<RequestState<List<GiftcardDto>>> {
        return flow {
            try {
                emit(RequestState.loading(null))
                val result = repo.getGiftcards()
                emit(RequestState.success(result))
            } catch (e: HttpException) {
                emit(RequestState.error(e.localizedMessage, null))
            } catch (e: IOException) {
                emit(RequestState.error(e.localizedMessage, null))
            }
        }
    }
}