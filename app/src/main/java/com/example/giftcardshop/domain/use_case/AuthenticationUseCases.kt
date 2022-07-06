package com.example.giftcardshop.domain.use_case

import com.example.giftcardshop.data.local.AuthDataStore
import com.example.giftcardshop.domain.domain_repository.AuthenticationRepository
import com.example.giftcardshop.domain.model.AuthStatus
import com.example.giftcardshop.shared.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val authDataStore: AuthDataStore
) {
    fun doAction(username: String, password: String): Flow<RequestState<Boolean>> {
        return flow {
            emit(RequestState.loading(null))
            try {
                coroutineScope {
                    val result = authenticationRepository.signIn(username, password)
                    if (result) {
                        emit(RequestState.success(true))
                        authDataStore.persistAuthStatus(AuthStatus(username, password, result))
                    } else {
                        emit(RequestState.success(false))
                    }
                }
            } catch (e: Exception) {
                emit(RequestState.error(e.message, null))
            }
        }
    }
}

class SignOutUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val authDataStore: AuthDataStore
) {
    fun doAction(): Flow<RequestState<Boolean>> {
        return flow {
            emit(RequestState.loading(null))
            try {
                coroutineScope {
                    val result = authenticationRepository.signOut()
                    if (result) {
                        emit(RequestState.success(true))
                        authDataStore.clearAuthPersistence()
                    } else {
                        emit(RequestState.success(false))
                    }
                }
            } catch (e: Exception) {
                emit(RequestState.error(e.message, null))
            }
        }
    }
}