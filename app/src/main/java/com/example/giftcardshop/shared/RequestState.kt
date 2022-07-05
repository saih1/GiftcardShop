package com.example.giftcardshop.shared

import com.example.giftcardshop.shared.Status.*

enum class Status {
    SUCCESS, ERROR, IDLE, LOADING
}

data class RequestState<out T>(
    val status: Status,
    val data: T?,
    val message: String?
) {
    companion object {
        fun <T> success(data: T): RequestState<T> =
            RequestState(SUCCESS, data, null)
        fun <T> error(msg: String?, data: T?): RequestState<T> =
            RequestState(ERROR, data, msg)
        fun <T> loading(data: T?): RequestState<T> =
            RequestState(LOADING, data, null)
        fun <T> idle(data: T?): RequestState<T> =
            RequestState(IDLE, data, null)
    }
}