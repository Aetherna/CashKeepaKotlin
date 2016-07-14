package com.aethernadev.cashkeepakotlin.main

import android.content.SharedPreferences
import com.aethernadev.cashkeepakotlin.CKApp

/**
 * Created by Aetherna on 2016-07-14.
 */
class MainInteractor(val sharedPreferences: SharedPreferences) {

    fun isAppConfigured(): Boolean {
        return sharedPreferences.getBoolean(CKApp.isAppConfiguredKey, false)
    }

    fun setAppIsConfigured() {
        sharedPreferences.edit().putBoolean(CKApp.isAppConfiguredKey, true).apply()
    }
}