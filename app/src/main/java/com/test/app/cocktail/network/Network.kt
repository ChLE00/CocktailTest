package com.test.app.cocktail.network

import kotlinx.coroutines.*
import retrofit2.HttpException

object Network {
    private val job = Job()
    private val coroutinesScope: CoroutineScope = CoroutineScope(job + Dispatchers.IO)
    fun defaultError(t: Throwable) {
        t.printStackTrace()
    }

    fun <T> request(call: Deferred<T>, callback: NetworkCallback<T>) {
        request(call, callback.success, callback.error)
    }

    private fun <T> request(
        call: Deferred<T>,
        onSuccess: ((T) -> Unit)?,
        onError: ((Throwable) -> Unit)?
    ) {
        coroutinesScope.launch {
            try {
                onSuccess?.let {
                    onSuccess(call.await())
                }
            } catch (httpException: HttpException) {
                // a non-2XX response was received
                defaultError(httpException)
            } catch (t: Throwable) {
                // a networking or data conversion error
                onError?.let {
                    onError(t)
                }
            }
        }
    }
}