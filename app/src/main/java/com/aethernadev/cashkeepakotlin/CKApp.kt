package com.aethernadev.cashkeepakotlin

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.aethernadev.cashkeepakotlin.base.SchedulersWrapper
import com.aethernadev.cashkeepakotlin.home.HomeInteractor
import com.aethernadev.cashkeepakotlin.home.HomePresenter
import com.aethernadev.cashkeepakotlin.main.MainInteractor
import com.aethernadev.cashkeepakotlin.main.MainPresenter
import com.aethernadev.cashkeepakotlin.repo.CashKeepaRepo
import com.aethernadev.cashkeepakotlin.repo.Repo
import com.aethernadev.cashkeepakotlin.setup.SetupCategoriesPresenter
import com.aethernadev.cashkeepakotlin.setup.SetupInteractor
import com.aethernadev.cashkeepakotlin.setup.SetupLimitPresenter
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.KodeinApplication
import com.github.salomonbrys.kodein.singleton
import io.realm.Realm
import io.realm.RealmConfiguration
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by Aetherna on 2016-07-14.
 */

class CKApp : Application(), KodeinApplication {

    val appConfigSharedPrefsName: String = "CashKeepaSharedPreferences"

    override val kodein = Kodein {

        bind<Realm>() with singleton { initRealm() }
        bind<Repo>() with singleton { CashKeepaRepo(instance()) }
        bind<SchedulersWrapper>() with singleton { SchedulersWrapper(Schedulers.computation(), AndroidSchedulers.mainThread()) }

        bind<MainInteractor>() with singleton { MainInteractor(getAppSharedPrefsFile()) }
        bind<MainPresenter>() with singleton { MainPresenter(instance()) }

        bind<HomeInteractor>() with singleton { HomeInteractor(instance(), instance()) }
        bind<HomePresenter>() with singleton { HomePresenter(instance()) }

        bind<SetupInteractor>() with singleton { SetupInteractor(instance()) }
        bind<SetupLimitPresenter>() with singleton { SetupLimitPresenter(instance()) }
        bind<SetupCategoriesPresenter>() with singleton { SetupCategoriesPresenter(instance()) }
    }

    fun initRealm(): Realm {
        val realmConfig = RealmConfiguration.Builder(this@CKApp).build()
        return Realm.getInstance(realmConfig)
    }

    fun getAppSharedPrefsFile(): SharedPreferences {
        return getSharedPreferences(appConfigSharedPrefsName, Context.MODE_PRIVATE)
    }

    companion object {
        val isAppConfiguredKey: String = "isAppConfigured"
    }

}