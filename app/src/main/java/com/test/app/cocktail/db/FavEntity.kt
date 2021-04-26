package com.test.app.cocktail.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Favorite_table")
data class FavEntity(
    @PrimaryKey(autoGenerate = true)
    val fav_id: Int,
    // @ColumnInfo (name = "Fav_Name")
    val fav_name: String,
    val fav_desc: String,
    val fav_alcohlic: String,
    val fav_thumb: String

) : Parcelable