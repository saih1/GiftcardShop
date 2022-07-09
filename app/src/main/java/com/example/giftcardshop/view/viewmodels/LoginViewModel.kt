package com.example.giftcardshop.view.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giftcardshop.domain.use_case.GetPersistenceAuthUseCase
import com.example.giftcardshop.domain.use_case.LoginUseCase
import com.example.giftcardshop.domain.use_case.LogoutUseCase
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
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getPersistenceAuthUseCase: GetPersistenceAuthUseCase
) : ViewModel() {

    private val _signInStatus: MutableStateFlow<RequestState<Boolean>> = MutableStateFlow(RequestState.success(false))
    val signInStatus: StateFlow<RequestState<Boolean>> = _signInStatus

    init {
        checkAutoLogin()
    }

    fun signIn(username: String, password: String) {
        loginUseCase.doAction(username, password).onEach {
            _signInStatus.value = it
        }.launchIn(viewModelScope)
    }

    fun signOut() {
        logoutUseCase.doAction().onEach {
            _signInStatus.value = it
        }.launchIn(viewModelScope)
    }

    private fun checkAutoLogin() {
        viewModelScope.launch {
            val result = getPersistenceAuthUseCase.doAction()
            if (result.authStatus) { signIn(result.username!!, result.password!!) }
        }
    }
}