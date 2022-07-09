package com.example.giftcardshop.domain.use_case

import com.example.giftcardshop.domain.domain_repository.GiftcardRepository
import com.example.giftcardshop.domain.model.Giftcard
import com.example.giftcardshop.shared.RequestState
import com.example.giftcardshop.shared.toGiftcard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetGiftcardsUseCase @Inject constructor(
    private val repo: GiftcardRepository
) {
    fun doAction(): Flow<RequestState<List<Giftcard>>> {
        return flow {
            try {
                emit(RequestState.loading(null))
                val giftcards: List<Giftcard> = repo
                    .getGiftcards()
                    .map { dto -> dto.toGiftcard() }
                emit(RequestState.success(giftcards))
            } catch (e: HttpException) {
                emit(RequestState.error(e.message, null))
            } catch (e: IOException) {
                emit(RequestState.error(e.message, null))
            } catch (e: Exception) {
                emit(RequestState.error(e.message, null))
            }
        }
    }
}