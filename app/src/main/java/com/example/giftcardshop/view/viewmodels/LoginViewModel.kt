package com.example.giftcardshop.view.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giftcardshop.domain.use_case.GetPersistenceAuthData
import com.example.giftcardshop.domain.use_case.SignInUseCase
import com.example.giftcardshop.domain.use_case.SignOutUseCase
import com.example.giftcardshop.shared.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val getPersistenceAuthData: GetPersistenceAuthData
) : ViewModel() {

    private val _signInStatus: MutableStateFlow<RequestState<Boolean>> = MutableStateFlow(RequestState.success(false))
    val signInStatus: StateFlow<RequestState<Boolean>> = _signInStatus

    init {
        checkAutoLogin()
    }

    fun signIn(username: String, password: String) {
        signInUseCase.doAction(username, password).onEach {
            _signInStatus.value = it
        }.launchIn(viewModelScope)
    }

    fun signOut() {
        signOutUseCase.doAction().onEach {
            _signInStatus.value = it
        }.launchIn(viewModelScope)
    }

    private fun checkAutoLogin() {
        viewModelScope.launch {
            val result = getPersistenceAuthData.doAction()
            if (result.authStatus) { signIn(result.username!!, result.password!!) }
        }
    }
}