package com.bsuir.cryptowallet.common.util

import android.content.Context
import android.content.SharedPreferences
import com.bsuir.cryptowallet.common.constant.APP_PREFERENCES

class SharedPreferencesUtil<T> {

    fun saveData(context: Context, key: String, value: T) {

        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        when (value) {
            is Int -> {
                editor.putInt(key, (value as Int).toInt())
            }
            is String -> {
                editor.putString(key, (value as String).toString())
            }
            is Boolean -> {
                editor.putBoolean(key, (value as Boolean))
            }
            is Long -> {
                editor.putLong(key, (value as Long))
            }
            is Float -> {
                editor.putFloat(key, (value as Float))
            }
        }
        editor.apply()
    }

    fun fetchData(context: Context, key: String, defaultValue: T): T {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        var result: T = defaultValue
        when (defaultValue) {
            is Int -> {
                result = sharedPreferences.getInt(key, (defaultValue as Int).toInt()) as T
            }
            is String -> {
                result = sharedPreferences.getString(key, (defaultValue as String).toString()) as T
            }
            is Boolean -> {
                result = sharedPreferences.getBoolean(key, (defaultValue as Boolean)) as T
            }
            is Long -> {
                result = sharedPreferences.getLong(key, (defaultValue as Long)) as T
            }
            is Float -> {
                result = sharedPreferences.getFloat(key, (defaultValue as Float)) as T
            }
        }
        return result
    }
}
