package com.test.app.cocktail.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefaceHelper {
    companion object {
        var sharedPreferences: SharedPreferences? = null
        private val editor: SharedPreferences.Editor? = null

        fun init(context: Context) {
            sharedPreferences =
                context.getSharedPreferences("cocktail_test", Context.MODE_PRIVATE)
        }

        fun saveByName(isChecked: Boolean) {
            sharedPreferences!!.edit().putBoolean("byName", isChecked).apply()
        }

        fun saveByAlphabet(isChecked: Boolean) {
            sharedPreferences!!.edit().putBoolean("byAlphabet", isChecked).apply()
        }

        val ByName: Boolean
            get() = (sharedPreferences!!.getBoolean("byName", true) ?: "") as Boolean

        val ByAlphabet: Boolean
            get() = (sharedPreferences!!.getBoolean("byAlphabet", true) ?: "") as Boolean

        fun removeByName() {
            sharedPreferences!!.edit().putBoolean("byName", false).apply()
        }

        fun removeByAlphabet() {
            sharedPreferences!!.edit().putBoolean("byAlphabet", false).apply()
        }

    }
}