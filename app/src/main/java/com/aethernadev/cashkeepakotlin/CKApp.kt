package com.aethernadev.cashkeepakotlin

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.aethernadev.cashkeepakotlin.home.HomeInteractor
import com.aethernadev.cashkeepakotlin.home.HomePresenter
import com.aethernadev.cashkeepakotlin.main.MainInteractor
import com.aethernadev.cashkeepakotlin.main.MainPresenter
import com.aethernadev.cashkeepakotlin.repo.CashKeepaRepo
import com.aethernadev.cashkeepakotlin.repo.Repo
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.KodeinApplication
import com.github.salomonbrys.kodein.singleton

/**
 * Created by Aetherna on 2016-07-14.
 */

class CKApp : Application(), KodeinApplication {

    val appConfigSharedPrefsName: String = "CashKeepaSharedPreferences"

    override val kodein = Kodein {
        bind<Repo>() with singleton { CashKeepaRepo() }

        bind<MainInteractor>() with singleton { MainInteractor(getAppSharedPrefsFile()) }//todo add shared prefs
        bind<MainPresenter>() with singleton { MainPresenter(instance()) }

        bind<HomeInteractor>() with singleton { HomeInteractor(instance()) }
        bind<HomePresenter>() with singleton { HomePresenter(instance()) }
    }

    fun getAppSharedPrefsFile(): SharedPreferences {
        return getSharedPreferences(appConfigSharedPrefsName, Context.MODE_PRIVATE)
    }

    companion object {
        val isAppConfiguredKey: String = "isAppConfigured"
    }

}