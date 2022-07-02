package com.example.giftcardshop.view.giftcard_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giftcardshop.domain.model.Giftcard
import com.example.giftcardshop.domain.use_case.GetGiftcardsUseCase
import com.example.giftcardshop.shared.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class GiftcardListViewModel @Inject constructor(
    private val getGiftcardsUseCase: GetGiftcardsUseCase
) : ViewModel() {

    private val _giftcards: MutableStateFlow<RequestState<List<Giftcard>>> =
        MutableStateFlow(RequestState.idle(null))
    val giftcards: StateFlow<RequestState<List<Giftcard>>> = _giftcards

    init {
        getGiftcards()
    }

    private fun getGiftcards() {
        getGiftcardsUseCase.doAction().onEach {
            _giftcards.value = it
        }.launchIn(viewModelScope)
    }
}