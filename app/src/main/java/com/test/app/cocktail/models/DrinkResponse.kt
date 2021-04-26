package com.test.app.cocktail.models


import com.google.gson.annotations.SerializedName

data class DrinkbyNameResponse(
    @SerializedName("drinks")
    val drinks: List<Drink?>?=null
)

data class Drink(
    @SerializedName("strAlcoholic")
    val strAlcoholic: String,
    @SerializedName("strDrink")
    val strDrink: String,
    @SerializedName("strDrinkThumb")
    val strDrinkThumb: String,
    @SerializedName("strInstructions")
    val strInstructions: String,
)

