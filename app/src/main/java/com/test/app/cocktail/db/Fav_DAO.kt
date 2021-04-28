package com.test.app.cocktail.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface Fav_DAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun saveFav(fav: FavEntity)

    @Query("SELECT * FROM FavoriteDB")
    fun fetchFav(): LiveData<List<FavEntity>>

    @Delete
    fun deleteFav(fav: FavEntity)
}