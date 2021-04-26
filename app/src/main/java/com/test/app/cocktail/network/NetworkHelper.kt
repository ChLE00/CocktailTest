package com.test.app.cocktail.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory.Companion.invoke
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import retrofit2.converter.gson.GsonConverterFactory

fun create(): ApiInterface {
    return Retrofit.Builder()
        .addCallAdapterFactory(invoke())
        .addConverterFactory(GsonConverterFactory.create()).client(
            OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS).build()
        ).baseUrl("https://www.thecocktaildb.com/api/json/v1/1/").build().create(
            ApiInterface::class.java
        )
}

