package com.test.app.cocktail.network

class NetworkCallback<T> {
    var success: ((T) -> Unit)? = null
    var error: ((Throwable) -> Unit)? = null
}