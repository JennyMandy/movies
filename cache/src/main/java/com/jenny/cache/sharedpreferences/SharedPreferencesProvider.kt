package com.jenny.cache.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import com.jenny.cache.constants.Constants
import javax.inject.Inject

class SharedPreferencesProvider @Inject
constructor(context: Context) {

    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(Constants.SHARED_PREFERENCES_STORE_NAME, Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor

    init {
        editor = sharedPreferences.edit()
    }
}