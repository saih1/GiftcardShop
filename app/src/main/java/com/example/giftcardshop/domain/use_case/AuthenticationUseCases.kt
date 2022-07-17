package com.example.giftcardshop.domain.use_case

import com.example.giftcardshop.domain.domain_repository.AuthenticationRepository
import com.example.giftcardshop.domain.domain_repository.PersistenceRepository
import com.example.giftcardshop.domain.model.AuthStatus
import com.example.giftcardshop.shared.RequestState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val persistenceRepository: PersistenceRepository
) {
    fun doAction(username: String, password: String): Flow<RequestState<Boolean>> {
        if (username.isEmpty() || password.isEmpty()) {
            throw Exception("Empty credentials")
        }
        return flow {
            try {
                coroutineScope {
                    emit(RequestState.loading(null))
                    val result = authenticationRepository.login(username, password)
                    if (result) {
                        emit(RequestState.success(true))
                        persistenceRepository.persistAuthStatus(
                            AuthStatus(
                                username = username,
                                password = password,
                                authStatus = result
                            )
                        )
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

class LogoutUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val persistenceRepository: PersistenceRepository
) {
    fun doAction(): Flow<RequestState<Boolean>> {
        return flow {
            try {
                coroutineScope {
                    emit(RequestState.loading(null))
                    val result = authenticationRepository.logout()
                    if (result) {
                        persistenceRepository.clearPersistence()
                        emit(RequestState.success(false))
                    } else {
                        emit(RequestState.success(true))
                    }
                }
            } catch (e: Exception) {
                emit(RequestState.error(e.message, null))
            }
        }
    }
}