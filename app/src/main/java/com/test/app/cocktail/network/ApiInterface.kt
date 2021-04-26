package com.test.app.cocktail.network

import com.test.app.cocktail.models.DrinkbyNameResponse
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @GET("search.php")
    fun getDrinkByNameAsync(
        @Query("s") s: String
    ): Deferred<Response<DrinkbyNameResponse>>

    @GET("search.php")
    fun getDrinkByAlphabetAsync(
        @Query("f") f: String
    ): Deferred<Response<DrinkbyNameResponse>>
}