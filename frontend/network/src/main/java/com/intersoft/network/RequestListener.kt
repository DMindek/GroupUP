package com.intersoft.network

interface RequestListener {
    fun <T> onSuccess(data : T)
    fun onError(error: String?)

}
