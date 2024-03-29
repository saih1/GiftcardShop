package com.example.giftcardshop.view.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giftcardshop.di.Dispatcher
import com.example.giftcardshop.di.GiftcardDispatchers
import com.example.giftcardshop.domain.model.Denomination
import com.example.giftcardshop.domain.model.Giftcard
import com.example.giftcardshop.domain.use_case.AddCartItemUseCase
import com.example.giftcardshop.domain.use_case.GetGiftcardsUseCase
import com.example.giftcardshop.shared.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GiftcardViewModel @Inject constructor(
    private val getGiftcardsUseCase: GetGiftcardsUseCase,
    private val addCartItemUseCase: AddCartItemUseCase,
    @Dispatcher(GiftcardDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _giftcards: MutableStateFlow<RequestState<List<Giftcard>>> =
        MutableStateFlow(RequestState.idle(null))
    val giftcards: StateFlow<RequestState<List<Giftcard>>> = _giftcards

    private val _selectedGiftcard: MutableStateFlow<Giftcard?> =
        MutableStateFlow(null)
    val selectedGiftcard: StateFlow<Giftcard?> = _selectedGiftcard

    init {
        getGiftcards()
    }

    private fun getGiftcards() {
        getGiftcardsUseCase.doAction().onEach {
            _giftcards.value = it
        }.launchIn(viewModelScope)
    }

    fun selectGiftcard(giftcard: Giftcard) {
        _selectedGiftcard.value = giftcard
    }

    fun addToCart(giftcard: Giftcard, denomination: Denomination) {
        viewModelScope.launch(ioDispatcher) {
            addCartItemUseCase.doAction(giftcard, denomination)
        }
    }
}