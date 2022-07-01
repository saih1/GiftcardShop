package com.example.giftcardshop.view.giftcard_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giftcardshop.data.data_repository.GiftcardRepository
import com.example.giftcardshop.data.network.dto.GiftcardDto
import com.example.giftcardshop.shared.Constants.TAG
import com.example.giftcardshop.shared.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class GiftcardListViewModel @Inject constructor(
    // use cases
    private val repository: GiftcardRepository
) : ViewModel() {
    private val _giftcards: MutableStateFlow<RequestState<List<GiftcardDto>>> =
        MutableStateFlow(RequestState.idle(null))
    val giftcards: StateFlow<RequestState<List<GiftcardDto>>> =
        _giftcards

    init {
        Log.i(TAG, "Initialising ViewModel")
        getGiftcards()
    }
    private fun getGiftcards() {
        _giftcards.value = RequestState.loading(null)
        viewModelScope.launch {
            try {
                val giftcards = repository.getGiftcards()
                _giftcards.value = RequestState.success(giftcards)
            } catch (e: HttpException) {
                _giftcards.value = RequestState.error(e.message, null)
            } catch (e: IOException) {
                _giftcards.value = RequestState.error(e.message, null)
            }
        }
    }
}