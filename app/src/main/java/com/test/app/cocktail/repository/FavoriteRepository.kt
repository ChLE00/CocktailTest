package com.test.app.cocktail.repository

import androidx.lifecycle.LiveData
import com.test.app.cocktail.db.FavEntity
import com.test.app.cocktail.db.Fav_DAO

class FavoriteRepository(private val favDao: Fav_DAO) {

    val readAllData: LiveData<List<FavEntity>> = favDao.fetchFav()

}