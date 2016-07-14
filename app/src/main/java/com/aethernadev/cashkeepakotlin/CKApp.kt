package com.aethernadev.cashkeepakotlin

import android.app.Application
import com.aethernadev.cashkeepakotlin.main.activity.MainInteractor
import com.aethernadev.cashkeepakotlin.main.activity.MainPresenter
import com.aethernadev.cashkeepakotlin.repo.CashKeepaRepo
import com.aethernadev.cashkeepakotlin.repo.Repo
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.KodeinApplication
import com.github.salomonbrys.kodein.singleton

/**
 * Created by Aetherna on 2016-07-14.
 */

class CKApp : Application(), KodeinApplication {
    override val kodein = Kodein {
        bind<Repo> () with singleton { CashKeepaRepo() }

        bind<MainInteractor>() with singleton { MainInteractor(instance()) }
        bind<MainPresenter>() with singleton { MainPresenter(instance()) }
    }
}