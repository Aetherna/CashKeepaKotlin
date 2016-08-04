package com.aethernadev.cashkeepakotlin.settings

import com.aethernadev.cashkeepakotlin.base.BaseInteractor
import com.aethernadev.cashkeepakotlin.base.SchedulersWrapper
import com.aethernadev.cashkeepakotlin.repo.Repo
import rx.Observable

/**
 * Created by Aetherna on 2016-08-04.
 */
open class SettingsInteractor(val repo: Repo, schedulers: SchedulersWrapper) : BaseInteractor(schedulers) {

    open fun getSettings(): Observable<Settings> {
        return wrapAsJustObservable { repo.getAppCurrency() }
    }
}