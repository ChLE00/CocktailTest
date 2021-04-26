package com.test.app.cocktail.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(FavEntity::class)], version = 2, exportSchema = false)
abstract class Localdb : RoomDatabase() {

    abstract fun fav_DAO(): Fav_DAO

    companion object {
        @Volatile
        private var INSTANCE: Localdb? = null

        fun favDB(context: Context): Localdb{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Localdb::class.java,
                    "fav_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}