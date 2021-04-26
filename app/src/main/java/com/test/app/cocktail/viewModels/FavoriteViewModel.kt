package com.test.app.cocktail.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.test.app.cocktail.db.FavEntity
import com.test.app.cocktail.db.Localdb
import com.test.app.cocktail.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    val fetchAllFav: LiveData<List<FavEntity>>
    private val repository: FavoriteRepository

    init {
        val favDAO = Localdb.favDB(
            application
        ).fav_DAO()
        repository = FavoriteRepository(favDAO)
        fetchAllFav = repository.readAllData
    }


}