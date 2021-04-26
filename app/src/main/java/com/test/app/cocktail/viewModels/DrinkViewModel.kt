package com.test.app.cocktail.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.app.cocktail.models.DrinkbyNameResponse
import com.test.app.cocktail.network.Resource
import com.test.app.cocktail.network.Status
import com.test.app.cocktail.network.create
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response

class DrinkViewModel : ViewModel() {
    private val job = Job()
    private val coroutinesScope: CoroutineScope = CoroutineScope(job + Dispatchers.IO)

    val drinksResponse = MutableLiveData<Resource<Response<DrinkbyNameResponse>?>>()

    fun drinkName() {
        drinksResponse.value =
            Resource<Response<DrinkbyNameResponse>?>(Status.LOADING, null, "Loading")

        coroutinesScope.launch {
            try {
                val res = create().getDrinkByNameAsync(
                    "margarita"
                ).await()

                if (res.isSuccessful && res.code() == 200) {
                    val response = res.body()
                    if (response != null) {
                        drinksResponse.postValue(
                            Resource<Response<DrinkbyNameResponse>?>(
                                Status.SUCCESS,
                                res,
                                "Drinks Fetched Successfully...!"
                            )
                        )
                    } else {
                        drinksResponse.postValue(
                            Resource<Response<DrinkbyNameResponse>?>(
                                Status.ERROR,
                                null,
                                "Drinks not Fetched...!"
                            )
                        )
                    }
                } else {
                    drinksResponse.postValue(
                        Resource<Response<DrinkbyNameResponse>?>(
                            Status.ERROR,
                            null,
                            "Drinks not Fetched...!"
                        )
                    )
                }
            } catch (e: Exception) {
                drinksResponse.postValue(
                    Resource<Response<DrinkbyNameResponse>?>(
                        Status.ERROR,
                        null,
                        "Drinks not Fetched...!"
                    )
                )
            }
        }
    }

    fun drinkAlphabet() {
        drinksResponse.value =
            Resource<Response<DrinkbyNameResponse>?>(Status.LOADING, null, "Loading")

        coroutinesScope.launch {
            try {
                val res = create().getDrinkByAlphabetAsync(
                    "a"
                ).await()

                if (res.isSuccessful && res.code() == 200) {
                    val response = res.body()
                    if (response != null) {
                        drinksResponse.postValue(
                            Resource<Response<DrinkbyNameResponse>?>(
                                Status.SUCCESS,
                                res,
                                "Drinks Fetched Successfully...!"
                            )
                        )
                    } else {
                        drinksResponse.postValue(
                            Resource<Response<DrinkbyNameResponse>?>(
                                Status.ERROR,
                                null,
                                "Drinks not Fetched...!"
                            )
                        )
                    }
                } else {
                    drinksResponse.postValue(
                        Resource<Response<DrinkbyNameResponse>?>(
                            Status.ERROR,
                            null,
                            "Drinks not Fetched...!"
                        )
                    )
                }
            } catch (e: Exception) {
                drinksResponse.postValue(
                    Resource<Response<DrinkbyNameResponse>?>(
                        Status.ERROR,
                        null,
                        "Drinks not Fetched...!"
                    )
                )
            }
        }
    }
}