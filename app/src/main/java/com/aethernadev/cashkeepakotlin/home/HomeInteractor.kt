package com.aethernadev.cashkeepakotlin.home

import com.aethernadev.cashkeepakotlin.base.BaseInteractor
import com.aethernadev.cashkeepakotlin.base.SchedulersWrapper
import com.aethernadev.cashkeepakotlin.models.Category
import com.aethernadev.cashkeepakotlin.repo.Repo
import org.joda.money.Money
import rx.Observable

/**
 * Created by Aetherna on 2016-07-12.
 */
open class HomeInteractor(val repo: Repo, schedulers: SchedulersWrapper) : BaseInteractor(schedulers) {

    open fun getTodayOutstandingLimit(): Money {
        return repo.getTodayOutstandingLimit()
    }

    open fun getCategories(onNext: (List<Category>) -> Unit, onError: (Throwable) -> Unit) {
        Observable.just(repo.getCategories())
                .subscribeOn(schedulers.ioScheduler)
                .observeOn(schedulers.uiScheduler)
                .subscribe(onNext, onError)
    }

}