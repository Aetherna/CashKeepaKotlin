package com.aethernadev.cashkeepakotlin.config

import com.aethernadev.cashkeepakotlin.base.BaseInteractor
import com.aethernadev.cashkeepakotlin.base.SchedulersWrapper
import com.aethernadev.cashkeepakotlin.repo.Repo
import org.joda.money.CurrencyUnit
import rx.Observable

/**
 * Created by Aetherna on 2016-08-04.
 */
open class ConfigInteractor(val repo: Repo, schedulers: SchedulersWrapper) : BaseInteractor(schedulers) {

    open fun getSettings(): Observable<CurrencyUnit> = wrapAsJustObservable { repo.getAppCurrency() }
}